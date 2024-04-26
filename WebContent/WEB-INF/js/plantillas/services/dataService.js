const getProcesos = async () => {
    console.log("a obtener procesos")
    try {
        data = await axios({
            method: 'get',
            url: '/sgdp/procesos/vigente',
        });
        console.log(data.data)
        data = data.data;
    } catch (error) {
        data = []
    }

    return data;
} 

const getDocumentos = async (idProceso) => {
    console.log("a obtener documentos")
    try {
        data = await axios({
            method: 'get',
            url: '/sgdp/documentoYtarea/'+idProceso,
        });
        console.log(data.data)
        data = data.data;
    } catch (error) {
        data = []
    }

    return data;
}

const getPlantillas = async () => {
    console.log("a obtener plantillas")
    try {
        data = await axios({
            method: 'get',
            url: '/sgdp/plantillas',
        });
        console.log(data.data)
        data = data.data;
    } catch (error) {
        data = []
    }

    return data;
}

const updateAsignaciones = async (list) => {
    console.log("a actualizar las asignaciones")
    try {
        data = await axios({
            method: 'put',
            url: '/sgdp/plantillaDocumento',
			data: list
        });
        console.log(data.data)
        data = data.data;
    } catch (error) {
        data = []
    }

    return data;
}

const savePlantilla = async (plantilla) => {
	console.log("a guardar la plantilla")
    try {
        data = await axios({
            method: 'post',
            url: '/sgdp/plantilla',
			data: plantilla
        });
    } catch (error) {
        data = []
    }

    return data;
}