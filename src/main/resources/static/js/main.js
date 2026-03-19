const baseURL = () => {
    let parametro = document.getElementById("categoriaInput").value;

    // URL de la API de categorías
    let url = "/categorias/" + parametro;

    fetch(url, { method: "GET" })
        .then((response) => {
            if (!response.ok) {
                throw new Error(`Error ${response.status}: No se encontró la categoría`);
            }
            return response.json();
        })
        .then((myJson) => {
            console.log(myJson);

            let element = document.getElementById("table");
            element.style.display = "block";

            // --- CAMBIO DINÁMICO DE IMAGEN ---
            // Si buscas el ID 1, cargará assets/img/1.jpg
            document.getElementById("image").src = "assets/img/" + myJson.idCategoria + ".jpg";

            // Si la imagen no existe, se pone la de terminalNotFound
            document.getElementById("image").onerror = function() {
                this.src = "assets/img/teminalNotFound.jpg";
            };

            // Llenado de datos (Los nombres coincidan con tu DTO)
            document.getElementById("parameterValue0").innerHTML = myJson.idCategoria;
            document.getElementById("parameterValue1").innerHTML = myJson.nombre;
            document.getElementById("parameterValue2").innerHTML = myJson.descripcion;

            let genera = myJson.generaCertificado ? "Sí" : "No";
            document.getElementById("parameterValue3").innerHTML = genera;
        })
        .catch((err) => {
            console.log(err);
            document.getElementById("table").style.display = "none";
            alert("No se pudo obtener la información de la categoría con ID: " + parametro);
        });
};