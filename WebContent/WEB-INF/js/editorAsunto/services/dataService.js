const getEkpediente = async expediente => {
    try {
        data = await axios({
            method: 'get',
            url: '/sgdp/editorDeAsunto/' + expediente,
        });
        data = data.data;
    } catch (error) {
        data = []
    }

    return data;
} 

const actualizarExpediente = async expediente => {
    try {
        data = await axios({
            method: 'put',
            url: '/sgdp/editorDeAsunto',
			data: expediente
        });
        data = data.data;
    } catch (error) {
        data = null;
    }

    return data;
}

