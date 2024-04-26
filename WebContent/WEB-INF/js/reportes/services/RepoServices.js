
const getRepoData = async (tipo, desde, hasta, filtro) => {
  let data = null;

 
  try {
    data = await axios({
      method: 'get',
      url: `/sgdp/reportes/${tipo}/${(desde)}/${(hasta)}/s-${filtro}`,
    });
    data = data.data;
  } catch (error) {
    data = [
      {
        usuario: "usuario",
        inDia: 12,
        outDia: 15,
        tasaIn: 8,
        tiempoRespuesta: 5,
        enBandeja: 12,
        area: "",
      },
      {
        usuario: "usuario",
        inDia: 12,
        outDia: 15,
        tasaIn: 8,
        tiempoRespuesta: 5,
        enBandeja: 12,
        area: "",
      },
    ];
  }

  console.log('el servicio devuelve', { data });

  return data;
}
