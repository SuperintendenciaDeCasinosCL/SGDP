
const getProcesos = async () => {
  let data = null;

  try {
    data = await axios({
      method: 'get',
      url: '/sgdp/asignacionUsuarios/procesos',
    });
  } catch (error) {
    data = [
      {
        "idProceso": 232,
        "descripcionProceso": "Proceso de prueba parametros 29oct prueba 2",
        "nombreProceso": "Proceso de prueba parametros 29oct prueba 2",
        "vigente": true,
        "diasHabilesMaxDuracion": 30,
        "nombreMacroproceso": "Auditoría interna",
        "codigoProceso": "00 00"
      },
      {
        "idProceso": 448,
        "descripcionProceso": "Atención de consultas",
        "nombreProceso": "Atención de consultas",
        "vigente": true,
        "diasHabilesMaxDuracion": 15,
        "nombreMacroproceso": "Atención del medio",
        "codigoProceso": "11 01"
      },
      {
        "idProceso": 445,
        "descripcionProceso": "Atención de reclamos",
        "nombreProceso": "Atención de reclamos",
        "vigente": true,
        "diasHabilesMaxDuracion": 56,
        "nombreMacroproceso": "Atención del medio",
        "codigoProceso": "11 02"
      },
      {
        "idProceso": 456,
        "descripcionProceso": "Atención de denuncias",
        "nombreProceso": "Atención de denuncias",
        "vigente": true,
        "diasHabilesMaxDuracion": 21,
        "nombreMacroproceso": "Atención del medio",
        "codigoProceso": "11 03"
      },
      {
        "idProceso": 264,
        "descripcionProceso": "Respuesta a solicitudes de acceso a la información Ley N° 20.285",
        "nombreProceso": "Respuesta a solicitudes de acceso a la información Ley N° 20.285",
        "vigente": true,
        "diasHabilesMaxDuracion": 74,
        "nombreMacroproceso": "Atención del medio",
        "codigoProceso": "12 01"
      },
      {
        "idProceso": 743,
        "descripcionProceso": "Autorización solicitudes homologación - Oficio - SSHI",
        "nombreProceso": "Autorización solicitudes homologación - Oficio - SSHI",
        "vigente": true,
        "diasHabilesMaxDuracion": 50,
        "nombreMacroproceso": "Autorizar laboratorios e implementos de juego",
        "codigoProceso": "31 01 01"
      },
      {
        "idProceso": 553,
        "descripcionProceso": "Notificación de información operacional de casinos municipales",
        "nombreProceso": "Notificación de información operacional de casinos municipales",
        "vigente": true,
        "diasHabilesMaxDuracion": 15,
        "nombreMacroproceso": "Fiscalizar casinos en operación",
        "codigoProceso": "35 05"
      },
      {
        "idProceso": 581,
        "descripcionProceso": "Oficio que solicita antecedentes o aclaración - FISA",
        "nombreProceso": "Oficio que solicita antecedentes o aclaración - FISA",
        "vigente": true,
        "diasHabilesMaxDuracion": 40,
        "nombreMacroproceso": "Sancionar",
        "codigoProceso": "35 13"
      },
      {
        "idProceso": 580,
        "descripcionProceso": "Oficio que reitera instrucción - FISA",
        "nombreProceso": "Oficio que reitera instrucción - FISA",
        "vigente": true,
        "diasHabilesMaxDuracion": 40,
        "nombreMacroproceso": "Sancionar",
        "codigoProceso": "35 14"
      },
      {
        "idProceso": 579,
        "descripcionProceso": "Oficio que otorga prórroga - FISA",
        "nombreProceso": "Oficio que otorga prórroga - FISA",
        "vigente": true,
        "diasHabilesMaxDuracion": 40,
        "nombreMacroproceso": "Planificación presupuestaria y formulación del plan de compras",
        "codigoProceso": "35 15"
      },
    ]
  }

  return data;
}

const getRolesService = async idProceso => {
  let data = null;

  try {
    data = await axios({
      method: 'get',
      url: `/sgdp/asignacionUsuarios/roles/${idProceso}`,
    });
	data = data.data
  } catch (error) {
    data = [

      {
        "id": 2435,
        "nombre": "Responsable de atención ciudadana",
        "idProceso": 445
      },
      {
        "id": 2437,
        "nombre": "Encargado de despacho de documentos",
        "idProceso": 445
      },
      {
        "id": 2436,
        "nombre": "Analista de solicitud ciudadana",
        "idProceso": 445
      },
      {
        "id": 2436,
        "nombre": "Analista de solicitud ciudadana",
        "idProceso": 445
      },
      {
        "id": 2435,
        "nombre": "Responsable de atención ciudadana",
        "idProceso": 445
      },
      {
        "id": 2437,
        "nombre": "Encargado de despacho de documentos",
        "idProceso": 445
      },
      {
        "id": 2436,
        "nombre": "Analista de solicitud ciudadana",
        "idProceso": 445
      },
      {
        "id": 2435,
        "nombre": "Responsable de atención ciudadana",
        "idProceso": 445
      },
      {
        "id": 2438,
        "nombre": "Administrador de documentos",
        "idProceso": 445
      },
      {
        "id": 2444,
        "nombre": "Tramitador de firma",
        "idProceso": 445
      },
      {
        "id": 2444,
        "nombre": "Tramitador de firma",
        "idProceso": 445
      },
      {
        "id": 2444,
        "nombre": "Tramitador de firma",
        "idProceso": 445
      },
      {
        "id": 2444,
        "nombre": "Tramitador de firma",
        "idProceso": 445
      },
      {
        "id": 2443,
        "nombre": "Aprobador final",
        "idProceso": 445
      },
      {
        "id": 2443,
        "nombre": "Aprobador final",
        "idProceso": 445
      },
      {
        "id": 2443,
        "nombre": "Aprobador final",
        "idProceso": 445
      },
      {
        "id": 2443,
        "nombre": "Aprobador final",
        "idProceso": 445
      },
      {
        "id": 2439,
        "nombre": "Revisor técnico - Jefe",
        "idProceso": 445
      },
      {
        "id": 2441,
        "nombre": "Revisor técnico - Profesional",
        "idProceso": 445
      },
      {
        "id": 2442,
        "nombre": "Revisor técnico - Coordinador",
        "idProceso": 445
      },
      {
        "id": 2442,
        "nombre": "Revisor técnico - Coordinador",
        "idProceso": 445
      },
      {
        "id": 2442,
        "nombre": "Revisor técnico - Coordinador",
        "idProceso": 445
      },
      {
        "id": 2435,
        "nombre": "Responsable de atención ciudadana",
        "idProceso": 445
      },
      {
        "id": 2439,
        "nombre": "Revisor técnico - Jefe",
        "idProceso": 445
      },
      {
        "id": 2437,
        "nombre": "Encargado de despacho de documentos",
        "idProceso": 445
      },
      {
        "id": 2436,
        "nombre": "Analista de solicitud ciudadana",
        "idProceso": 445
      },
      {
        "id": 2438,
        "nombre": "Administrador de documentos",
        "idProceso": 445
      },
      {
        "id": 2444,
        "nombre": "Tramitador de firma",
        "idProceso": 445
      },
      {
        "id": 2444,
        "nombre": "Tramitador de firma",
        "idProceso": 445
      },
      {
        "id": 2444,
        "nombre": "Tramitador de firma",
        "idProceso": 445
      },
      {
        "id": 2443,
        "nombre": "Aprobador final",
        "idProceso": 445
      },
      {
        "id": 2443,
        "nombre": "Aprobador final",
        "idProceso": 445
      },
      {
        "id": 2443,
        "nombre": "Aprobador final",
        "idProceso": 445
      },
      {
        "id": 2443,
        "nombre": "Aprobador final",
        "idProceso": 445
      },
    ]
  }


  return data;


}

const getUsuarios = async idRol => {
  let data = null;

  try {
    data = await axios({
      method: 'get',
      url: `/sgdp/asignacionUsuarios/usuarios/${idRol}`,
    });
    data = data.data;
  } catch (error) {
    data = {
      "asignados": [
        {
          "idUsuarioRol": 140,
          "idUsuario": "user1",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 6,
          "nombreRol": "PROFESIONAL TI",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 141,
          "idUsuario": "user1",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 7,
          "nombreRol": "ANALISTA UGES",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 139,
          "idUsuario": "user1",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 5,
          "nombreRol": "SECRETARIA",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 135,
          "idUsuario": "user1",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 1,
          "nombreRol": "OFICINA DE PARTES",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 142,
          "idUsuario": "user1",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 8,
          "nombreRol": "COORDINADOR",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 136,
          "idUsuario": "user1",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 2,
          "nombreRol": "JEFE DE DIVISION",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 138,
          "idUsuario": "user1",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 4,
          "nombreRol": "SUPERINTENDENTE(A)",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 137,
          "idUsuario": "user1",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 3,
          "nombreRol": "PROFESIONAL",
          "idUnidad": 0,
          "nombreUnidad": ""
        }
      ],
      "noAsignados": [
        {
          "idUsuarioRol": 144,
          "idUsuario": "vmenares",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 2,
          "nombreRol": "JEFE DE DIVISION",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 134,
          "idUsuario": "jriffo",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 8,
          "nombreRol": "COORDINADOR",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 204,
          "idUsuario": "fiscalizacion",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 1,
          "nombreRol": "OFICINA DE PARTES",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 150,
          "idUsuario": "vmenares",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 8,
          "nombreRol": "COORDINADOR",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 13,
          "idUsuario": "user3",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 5,
          "nombreRol": "SECRETARIA",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 146,
          "idUsuario": "vmenares",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 4,
          "nombreRol": "SUPERINTENDENTE(A)",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 132,
          "idUsuario": "jriffo",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 6,
          "nombreRol": "PROFESIONAL TI",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 5,
          "idUsuario": "fmolins",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 4,
          "nombreRol": "SUPERINTENDENTE(A)",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 148,
          "idUsuario": "vmenares",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 6,
          "nombreRol": "PROFESIONAL TI",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 147,
          "idUsuario": "vmenares",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 5,
          "nombreRol": "SECRETARIA",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 133,
          "idUsuario": "jriffo",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 7,
          "nombreRol": "ANALISTA UGES",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 145,
          "idUsuario": "vmenares",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 3,
          "nombreRol": "PROFESIONAL",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 149,
          "idUsuario": "vmenares",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 7,
          "nombreRol": "ANALISTA UGES",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 131,
          "idUsuario": "jriffo",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 5,
          "nombreRol": "SECRETARIA",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 4,
          "idUsuario": "egomez",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 1,
          "nombreRol": "OFICINA DE PARTES",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 151,
          "idUsuario": "ibesgdp",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 1,
          "nombreRol": "OFICINA DE PARTES",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 205,
          "idUsuario": "sshi",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 1,
          "nombreRol": "OFICINA DE PARTES",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 127,
          "idUsuario": "jriffo",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 1,
          "nombreRol": "OFICINA DE PARTES",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 129,
          "idUsuario": "jriffo",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 3,
          "nombreRol": "PROFESIONAL",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 128,
          "idUsuario": "jriffo",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 2,
          "nombreRol": "JEFE DE DIVISION",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 130,
          "idUsuario": "jriffo",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 4,
          "nombreRol": "SUPERINTENDENTE(A)",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 143,
          "idUsuario": "vmenares",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 1,
          "nombreRol": "OFICINA DE PARTES",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 50,
          "idUsuario": "dfsgdp",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 6,
          "nombreRol": "PROFESIONAL TI",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 203,
          "idUsuario": "sancionatorio",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 1,
          "nombreRol": "OFICINA DE PARTES",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 52,
          "idUsuario": "isaynsgdp",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 6,
          "nombreRol": "PROFESIONAL TI",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 202,
          "idUsuario": "opartesvirtual",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 1,
          "nombreRol": "OFICINA DE PARTES",
          "idUnidad": 0,
          "nombreUnidad": ""
        },
        {
          "idUsuarioRol": 12,
          "idUsuario": "user2",
          "activo": true,
          "fueraDeOficina": false,
          "idRol": 2,
          "nombreRol": "JEFE DE DIVISION",
          "idUnidad": 0,
          "nombreUnidad": ""
        }
      ],
      "rol": 2436
    }
  }

  return data;


}

const postUsuarios = async (usuarios, idRol) => {

  let data = null;

  try {
    data = await axios({
      method: 'post',
      url: `/sgdp/asignacionUsuarios/usuarios/${idRol}`,
      data: usuarios
    });
    return data.status === 200; 
  } catch (error) {
    console.log(error)
    return false
  }
}
