// FUNCIÓN 1: BUSCAR INDIVIDUAL (Tu lógica original intacta)
const baseURL = () => {
    ocultarSeccionesCat(); // Limpia la pantalla antes de mostrar el resultado
    let parametro = document.getElementById("categoriaInput").value;
    let url = "/categorias/" + parametro;

    fetch(url, { method: "GET" })
        .then((response) => {
            if (!response.ok) throw new Error("No encontrado");
            return response.json();
        })
        .then((myJson) => {
            let element = document.getElementById("table");
            element.style.display = "block";

            // Imagen dinámica
            document.getElementById("image").src = "assets/img/" + myJson.idCategoria + ".jpg";
            document.getElementById("image").onerror = function() {
                this.src = "assets/img/teminalNotFound.jpg";
            };

            // Llenado de datos
            document.getElementById("parameterValue0").innerHTML = myJson.idCategoria;
            document.getElementById("parameterValue1").innerHTML = myJson.nombre;
            document.getElementById("parameterValue2").innerHTML = myJson.descripcion;
            document.getElementById("parameterValue3").innerHTML = myJson.generaCertificado ? "Sí" : "No";
        })
        .catch((err) => {
            document.getElementById("table").style.display = "none";
            alert("No se encontró la categoría con ID: " + parametro);
        });
};

// FUNCIÓN 2: MOSTRAR FORMULARIO (La que te faltaba)
function mostrarFormularioCategoria() {
    ocultarSeccionesCat();
    const formulario = document.getElementById("seccionFormularioCat");
    formulario.classList.remove("d-none"); // Aquí es donde se hace visible
}

// FUNCIÓN 3: LISTAR TODAS (GET)
async function listarTodasCategorias() {
    ocultarSeccionesCat();
    try {
        const resp = await fetch("/categorias");
        const data = await resp.json();
        const body = document.getElementById("tablaBodyCat");
        body.innerHTML = "";

        data.forEach(cat => {
            body.innerHTML += `
                <tr>
                    <td>${cat.idCategoria}</td>
                    <td>${cat.nombre}</td>
                    <td>${cat.descripcion || '—'}</td>
                    <td><span class="badge ${cat.generaCertificado ? 'bg-success' : 'bg-secondary'}">
                        ${cat.generaCertificado ? 'Sí' : 'No'}</span></td>
                </tr>`;
        });
        document.getElementById("seccionTablaCat").classList.remove("d-none");
    } catch (e) {
        alert("Error al conectar con el servidor");
    }
}

// FUNCIÓN 4: GUARDAR CATEGORÍA (POST)
document.getElementById("formCategoria").onsubmit = async (e) => {
    e.preventDefault();
    const payload = {
        nombre: document.getElementById("catNombre").value,
        descripcion: document.getElementById("catDescripcion").value,
        generaCertificado: document.getElementById("catCertificado").value === "true"
    };

    const resp = await fetch("/categorias", {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(payload)
    });

    if (resp.ok) {
        ocultarSeccionesCat();
        const msg = document.getElementById("mensajeExitoCat");
        msg.textContent = "¡Categoría guardada exitosamente!";
        msg.classList.remove("d-none");
        listarTodasCategorias(); // Refresca la tabla automáticamente
    }
};

// FUNCIÓN 5: LIMPIAR PANTALLA
function ocultarSeccionesCat() {
    document.getElementById("seccionFormularioCat").classList.add("d-none");
    document.getElementById("seccionTablaCat").classList.add("d-none");
    document.getElementById("table").style.display = "none";
    document.getElementById("mensajeExitoCat").classList.add("d-none");
}