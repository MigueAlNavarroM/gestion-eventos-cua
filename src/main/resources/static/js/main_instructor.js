const API_URL = "/instructores";

const elements = {
    searchForm: document.getElementById("searchForm"),
    searchInput: document.getElementById("instructorSearch"),
    btnTodos: document.getElementById("btnTodos"),
    btnMostrarFormulario: document.getElementById("btnMostrarFormulario"),
    btnCancelarFormulario: document.getElementById("btnCancelarFormulario"),
    formInstructor: document.getElementById("formInstructor"),
    feedback: document.getElementById("feedback"),
    detalleCard: document.getElementById("detalleCard"),
    tablaCard: document.getElementById("tablaCard"),
    formCard: document.getElementById("formCard"),
    tablaBody: document.getElementById("tablaInstructoresBody"),
    totalResultados: document.getElementById("totalResultados"),
    detalleId: document.getElementById("detalleId"),
    detalleNombre: document.getElementById("detalleNombre"),
    detalleCorreo: document.getElementById("detalleCorreo"),
    detalleTelefono: document.getElementById("detalleTelefono"),
    detalleEspecialidad: document.getElementById("detalleEspecialidad"),
    detalleBio: document.getElementById("detalleBio"),
    detalleCategorias: document.getElementById("detalleCategorias"),
    badgeActivo: document.getElementById("badgeActivo")
};

function showFeedback(message, type = "info") {
    elements.feedback.className = `alert alert-${type} mt-4 mb-0`;
    elements.feedback.textContent = message;
    elements.feedback.classList.remove("d-none");
}

function hideFeedback() {
    elements.feedback.classList.add("d-none");
    elements.feedback.textContent = "";
}

function hideSection(section) {
    section.classList.add("d-none");
}

function showSection(section) {
    section.classList.remove("d-none");
}

function limpiarVista() {
    hideSection(elements.detalleCard);
    hideSection(elements.tablaCard);
}

function valorSeguro(value) {
    return value === null || value === undefined || value === "" ? "—" : value;
}

function nombreCompleto(instructor) {
    return [instructor.nombre, instructor.apellidoPaterno, instructor.apellidoMaterno]
        .filter(Boolean)
        .join(" ");
}

async function obtenerCategoriasInstructor(idInstructor) {
    try {
        const response = await fetch(`${API_URL}/${idInstructor}/categorias`, { method: "GET" });
        if (!response.ok) {
            return [];
        }
        return await response.json();
    } catch (error) {
        console.error("No se pudieron obtener las categorías del instructor", error);
        return [];
    }
}

async function enriquecerInstructor(instructor) {
    const categorias = await obtenerCategoriasInstructor(instructor.idInstructor);
    return {
        ...instructor,
        categorias
    };
}

function renderDetalle(instructor) {
    elements.detalleId.textContent = valorSeguro(instructor.idInstructor);
    elements.detalleNombre.textContent = valorSeguro(nombreCompleto(instructor));
    elements.detalleCorreo.textContent = valorSeguro(instructor.correo);
    elements.detalleTelefono.textContent = valorSeguro(instructor.telefono);
    elements.detalleEspecialidad.textContent = valorSeguro(instructor.especialidad);
    elements.detalleBio.textContent = valorSeguro(instructor.bio);

    const categoriasTexto = instructor.categorias && instructor.categorias.length > 0
        ? instructor.categorias.map(cat => cat.nombre ?? `Categoría ${cat.idCategoria}`).join(", ")
        : "Sin categorías registradas";

    elements.detalleCategorias.textContent = categoriasTexto;
    elements.badgeActivo.textContent = instructor.activo ? "Activo" : "Inactivo";
    elements.badgeActivo.className = instructor.activo ? "badge text-bg-success" : "badge text-bg-secondary";

    showSection(elements.detalleCard);
}

function renderTabla(instructores) {
    elements.tablaBody.innerHTML = "";

    if (!instructores.length) {
        elements.tablaBody.innerHTML = `
            <tr>
                <td colspan="7" class="text-center py-4">No se encontraron instructores.</td>
            </tr>
        `;
    } else {
        instructores.forEach(instructor => {
            const categoriasTexto = instructor.categorias && instructor.categorias.length > 0
                ? instructor.categorias.map(cat => cat.nombre ?? `Categoría ${cat.idCategoria}`).join(", ")
                : "Sin categorías";

            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${valorSeguro(instructor.idInstructor)}</td>
                <td>${valorSeguro(nombreCompleto(instructor))}</td>
                <td>${valorSeguro(instructor.correo)}</td>
                <td>${valorSeguro(instructor.telefono)}</td>
                <td>${valorSeguro(instructor.especialidad)}</td>
                <td>${instructor.activo ? "Sí" : "No"}</td>
                <td>${categoriasTexto}</td>
            `;
            elements.tablaBody.appendChild(row);
        });
    }

    elements.totalResultados.textContent = `${instructores.length} resultado(s)`;
    showSection(elements.tablaCard);
}

async function obtenerTodosLosInstructores() {
    const response = await fetch(API_URL, { method: "GET" });
    if (!response.ok) {
        throw new Error("No se pudo obtener la lista de instructores.");
    }
    return await response.json();
}

async function buscarPorId(id) {
    const response = await fetch(`${API_URL}/${id}`, { method: "GET" });
    if (!response.ok) {
        if (response.status === 404) {
            throw new Error(`No se encontró un instructor con el ID ${id}.`);
        }
        throw new Error("No se pudo obtener el instructor solicitado.");
    }
    return await response.json();
}

async function mostrarTodos() {
    hideFeedback();
    hideSection(elements.formCard);
    limpiarVista();

    try {
        const instructoresBase = await obtenerTodosLosInstructores();
        const instructores = await Promise.all(instructoresBase.map(enriquecerInstructor));
        renderTabla(instructores);
        showFeedback("Lista completa de instructores cargada correctamente.", "success");
    } catch (error) {
        console.error(error);
        showFeedback(error.message, "danger");
    }
}

async function ejecutarBusqueda(event) {
    event.preventDefault();
    hideFeedback();
    hideSection(elements.formCard);
    limpiarVista();

    const criterio = elements.searchInput.value.trim();

    if (!criterio) {
        showFeedback("Escribe un ID o un nombre para realizar la búsqueda.", "warning");
        return;
    }

    try {
        if (/^\d+$/.test(criterio)) {
            const instructorBase = await buscarPorId(criterio);
            const instructor = await enriquecerInstructor(instructorBase);
            renderDetalle(instructor);
            showFeedback(`Instructor con ID ${criterio} encontrado correctamente.`, "success");
            return;
        }

        const instructoresBase = await obtenerTodosLosInstructores();
        const filtradosBase = instructoresBase.filter(instructor => {
            const texto = nombreCompleto(instructor).toLowerCase();
            return texto.includes(criterio.toLowerCase());
        });

        if (!filtradosBase.length) {
            throw new Error(`No se encontraron instructores con el nombre "${criterio}".`);
        }

        const instructores = await Promise.all(filtradosBase.map(enriquecerInstructor));

        if (instructores.length === 1) {
            renderDetalle(instructores[0]);
        }

        renderTabla(instructores);
        showFeedback(`Se encontraron ${instructores.length} instructor(es) para "${criterio}".`, "success");
    } catch (error) {
        console.error(error);
        showFeedback(error.message, "danger");
    }
}

function parsearCategorias(input) {
    const texto = input.trim();
    if (!texto) {
        return [];
    }

    return texto
        .split(",")
        .map(valor => valor.trim())
        .filter(valor => valor !== "" && !Number.isNaN(Number(valor)))
        .map(valor => ({ idCategoria: Number(valor) }));
}

async function guardarInstructor(event) {
    event.preventDefault();
    hideFeedback();

    const payload = {
        nombre: document.getElementById("nombre").value.trim(),
        apellidoPaterno: document.getElementById("apellidoPaterno").value.trim() || null,
        apellidoMaterno: document.getElementById("apellidoMaterno").value.trim() || null,
        correo: document.getElementById("correo").value.trim() || null,
        telefono: document.getElementById("telefono").value.trim() || null,
        especialidad: document.getElementById("especialidad").value.trim() || null,
        bio: document.getElementById("bio").value.trim() || null,
        activo: document.getElementById("activo").value === "true",
        categorias: parsearCategorias(document.getElementById("categorias").value)
    };

    if (!payload.nombre) {
        showFeedback("El nombre del instructor es obligatorio.", "warning");
        return;
    }

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            const message = await response.text();
            throw new Error(message || "No se pudo guardar el instructor.");
        }

        const instructorGuardado = await response.json();
        const instructorCompleto = await enriquecerInstructor(instructorGuardado);

        elements.formInstructor.reset();
        document.getElementById("activo").value = "true";
        hideSection(elements.formCard);
        limpiarVista();
        renderDetalle(instructorCompleto);
        showFeedback(`Instructor registrado correctamente con ID ${instructorGuardado.idInstructor}.`, "success");
    } catch (error) {
        console.error(error);
        showFeedback(error.message, "danger");
    }
}

function mostrarFormulario() {
    hideFeedback();
    limpiarVista();
    showSection(elements.formCard);
}

function ocultarFormulario() {
    hideSection(elements.formCard);
}

elements.searchForm.addEventListener("submit", ejecutarBusqueda);
elements.btnTodos.addEventListener("click", mostrarTodos);
elements.btnMostrarFormulario.addEventListener("click", mostrarFormulario);
elements.btnCancelarFormulario.addEventListener("click", ocultarFormulario);
elements.formInstructor.addEventListener("submit", guardarInstructor);