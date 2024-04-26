
const getMacroProcesos = async (perspectiva) => {

    try {
        data = await axios({
            method: 'get',
            url: '/sgdp/serieDocumental/macroProcesos/' + perspectiva,
        });

        data = data.data;
    } catch (error) {
        data = []
    }

    return data;
}

const getSuperProcesos = async (macroProceso) => {
    try {
        data = await axios({
            method: 'get',
            url: '/sgdp/serieDocumental/superProcesos/' + macroProceso,
        });
        data = data.data;
    } catch (error) {
        data = []
    }

    return data;
}

const getProcesos = async (superProceso) => {
    try {
        data = await axios({
            method: 'get',
            url: '/sgdp/serieDocumental/procesos/' + superProceso,
        });
        data = data.data;
    } catch (error) {
        data = []
    }

    return data;
}

const listarSeriesDocumentales = async () => {
    try {
        data = await axios({
            method: 'get',
            url: '/sgdp/serieDocumental/list',
        });
        data = data.data;
    } catch (error) {
        data = []
    }

    return data;
}

const listarTiposDocumento = async (codigoProceso) => {
    try {
        data = await axios({
            method: 'get',
            url: '/sgdp/serieDocumental/tablaRetencion/' + codigoProceso,
        });
        data = data.data;
    } catch (error) {
        data = []
    }

    return data;
}

const guardarSerieDocumental = async (serie) => {
    try {
        const data = await axios({
            method: 'post',
            url: `/sgdp/serieDocumental/`,
            data: serie,
        });
        return data.status === 200
    } catch (error) {
        console.log(error)
    }

    return false;
}

const actualizarSerieDocumental = async (serie) => {
    try {
        const data = await axios({
            method: 'put',
            url: `/sgdp/serieDocumental/`,
            data: serie,
        });
        return data.status === 200
    } catch (error) {
        console.log(error)
    }

    return false;
}

const eliminarSerieDocumental = async (serie) => {
    try {
        const data = await axios({
            method: 'delete',
            url: `/sgdp/serieDocumental/`,
            data: serie,
        });
        return data.status === 200
    } catch (error) {
        console.log(error)
    }

    return false;
}

const actualizarTablaRetencion = async (doc) => {
    try {
        const data = await axios({
            method: 'put',
            url: `/sgdp/serieDocumental/tablaRetencion`,
            data: doc,
        });
        return data.status === 200
    } catch (error) {
        console.log(error)
    }

    return false;
}
