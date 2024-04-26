
const getInitialData = async () => {
	
    let data = null;

    try {
        data = await axios({
            method: 'get',
            url: '/sgdp/cargaProcesos/dataInicial',
        });
        data = data.data;
    } catch (error) {
        data = {
            "perspectivas": [
                {
                    "id": 4,
                    "descripcion": "Procesos dispuestos para gestionar y dirigir la SCJ, según facultades que entrega la Ley",
                    "nombre": "Gestión y dirección"
                },
                {
                    "id": 5,
                    "descripcion": "Procesos que se deben realizar para logarr objetivos encomendados por Ley",
                    "nombre": "Procesos esenciales"
                },
                {
                    "id": 9,
                    "descripcion": "Procesos que permiten hacer funcionar a la SCJ",
                    "nombre": "Procesos transversales"
                },
                {
                    "id": 10,
                    "descripcion": "Con usuarios industria casinos y acciones para informar a la opinión pública",
                    "nombre": "Relaciones con el medio"
                }
            ],
            "macroProcesos": [
                {
                    "idMacroProceso": 17,
                    "descripcionMacroProceso": null,
                    "nombreMacroProceso": "Asesoría y defensa jurídica",
                    "idPerspectiva": 9
                },
                {
                    "idMacroProceso": 18,
                    "descripcionMacroProceso": null,
                    "nombreMacroProceso": "Auditoría interna",
                    "idPerspectiva": 9
                },
                {
                    "idMacroProceso": 4,
                    "descripcionMacroProceso": null,
                    "nombreMacroProceso": "Autorizar funcionamiento de los casinos de juego",
                    "idPerspectiva": 5
                },
                {
                    "idMacroProceso": 13,
                    "descripcionMacroProceso": null,
                    "nombreMacroProceso": "Autorizar laboratorios e implementos de juego",
                    "idPerspectiva": 5
                },
                {
                    "idMacroProceso": 6,
                    "descripcionMacroProceso": null,
                    "nombreMacroProceso": "Homologación",
                    "idPerspectiva": 4
                },
                {
                    "idMacroProceso": 10,
                    "descripcionMacroProceso": null,
                    "nombreMacroProceso": "Atención del medio",
                    "idPerspectiva": 5
                },
                {
                    "idMacroProceso": 20,
                    "descripcionMacroProceso": null,
                    "nombreMacroProceso": "Ejecución presupuestaria y del plan de compras",
                    "idPerspectiva": 9
                },
                {
                    "idMacroProceso": 16,
                    "descripcionMacroProceso": null,
                    "nombreMacroProceso": "Sancionar",
                    "idPerspectiva": 5
                },
                {
                    "idMacroProceso": 14,
                    "descripcionMacroProceso": null,
                    "nombreMacroProceso": "Fiscalizar casinos en operación",
                    "idPerspectiva": 5
                },
                {
                    "idMacroProceso": 9,
                    "descripcionMacroProceso": null,
                    "nombreMacroProceso": "Control de gestión",
                    "idPerspectiva": 4
                },
                {
                    "idMacroProceso": 8,
                    "descripcionMacroProceso": null,
                    "nombreMacroProceso": "Planificación presupuestaria y formulación del plan de compras",
                    "idPerspectiva": 4
                },
                {
                    "idMacroProceso": 3,
                    "descripcionMacroProceso": null,
                    "nombreMacroProceso": "Planificación estratégica",
                    "idPerspectiva": 4
                }
            ],
            "unidades": [
                {
                    "idUnidad": 4,
                    "codigoUnidad": "UAYF",
                    "nombreCompletoUnidad": "Unidad de Administración y Finanzas"
                },
                {
                    "idUnidad": 2,
                    "codigoUnidad": "DJUR",
                    "nombreCompletoUnidad": "División Jurídica"
                },
                {
                    "idUnidad": 3,
                    "codigoUnidad": "GAB",
                    "nombreCompletoUnidad": "Gabinete"
                },
                {
                    "idUnidad": 5,
                    "codigoUnidad": "DAUT",
                    "nombreCompletoUnidad": "División de Autorizaciones"
                },
                {
                    "idUnidad": 1,
                    "codigoUnidad": "UTDP",
                    "nombreCompletoUnidad": "Unidad de Tecnologia y Desarrollo de Procesos"
                },
                {
                    "idUnidad": 6,
                    "codigoUnidad": "DFIS",
                    "nombreCompletoUnidad": "División de Fiscalización"
                },
                {
                    "idUnidad": 7,
                    "codigoUnidad": "UAYC",
                    "nombreCompletoUnidad": "Unidad de Atención Ciudadana y Comunicaciones"
                },
                {
                    "idUnidad": 8,
                    "codigoUnidad": "UAUD",
                    "nombreCompletoUnidad": "Unidad de Auditoría Interna"
                },
                {
                    "idUnidad": 9,
                    "codigoUnidad": "COMGR",
                    "nombreCompletoUnidad": "Comité de Gestión de Riesgos"
                },
                {
                    "idUnidad": 10,
                    "codigoUnidad": "UDE",
                    "nombreCompletoUnidad": "Unidad de Estudios"
                },
                {
                    "idUnidad": 11,
                    "codigoUnidad": "UGEC",
                    "nombreCompletoUnidad": "Unidad de Gestión Estratégica y de Clientes"
                },
                {
                    "idUnidad": 12,
                    "codigoUnidad": "UGIP",
                    "nombreCompletoUnidad": "Unidad de Gestión Interna y Personas"
                },
                {
                    "idUnidad": 13,
                    "codigoUnidad": "UAIC",
                    "nombreCompletoUnidad": "Unidad de Asuntos Institucionales y Comunicaciones"
                }
            ]
        }
    }

    return data;


}

const getMacroProcesos = async (perspectiva) => {

    try {
        data = await axios({
            method: 'get',
            url: '/sgdp/cargaProcesos/macroProcesos/' + perspectiva,
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
            url: '/sgdp/cargaProcesos/superProcesos/' + macroProceso,
        });
        data = data.data;
    } catch (error) {
        data = []
    }

    return data;
}


const sendProcess = async (process) => {
    try {
        const data = await axios({
            method: 'post',
            url: '/sgdp/cargaProcesos/',
            data: process
        });
        return data.status === 200 ? data.data.idProceso : 0;
    } catch (error) {
        console.log(error)
    }

    return 0;
}

const sendProcessAssMultipart = async (formData) => {
    try {
        const data = await axios.post(
			'/sgdp/cargaProcesosMultipart/',
            formData,
            {headers: {
                'Content-Type': 'multipart/form-data;charset=UTF-8'
            }
        });
        return data;
    } catch (error) {
        console.log(error)
    }

    return null;
}
const sendProcessImage = async (processID, image) => {
    try {
        const data = await axios({
            method: 'post',
            url: `/sgdp/cargaProcesos/${processID}`,
            data: {
                image
            },
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        return data.status === 200
    } catch (error) {
        console.log(error)
    }

    return false;
}