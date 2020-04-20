//funcion para agrandar o achicar el tamaño de letra (25/09/2003)
		var max_size = 150;
		var min_size = 50;
		var logo = document.getElementById('logorep');
		logo.innerHTML = '<input type="button" onclick="window.open(\'http://172.16.10.142/redmine/projects/sistema-de-fiscalizacion/issues/new\', \'_blank\')" value="Reportar error" />';
		//logo[0].innerHTML = 'aaa';
		//alert(logo.length);
		function dzIncreaseFontSize(idElemento) {
			
			if (document.all || document.getElementById) {	
				var elemento = document.all ? document.all[idElemento] : document.getElementById(idElemento);
				if (elemento) {	
					// el valor está; indicado en porcentaje:
					if(tamagnoLetras[idElemento] >= max_size){
						alert("No es posible aumentar más el texto.")
					}else{
						tamagnoLetras[idElemento] += 10;
						if (elemento.length) 
							for (i=0; i < elemento.length; i++) {
								elemento[i].style.fontSize = (tamagnoLetras[idElemento]+'%');
							}
						else
							elemento.style.fontSize = (tamagnoLetras[idElemento]+'%');
					}
				}
			}
		}
		
		function dzDecreaseFontSize(idElemento) {
			if (document.all || document.getElementById) {	
				var elemento = document.all ? document.all[idElemento] : document.getElementById(idElemento);
				if (elemento) {	
					// el valor est&aacute; indicado en porcentaje:
					if(tamagnoLetras[idElemento] <= min_size){
						alert("No es posible disminuir más el texto.")
					}else{
						tamagnoLetras[idElemento] -= 10;
						if (elemento.length) 
							for (i=0; i < elemento.length; i++) {
								elemento[i].style.fontSize = (tamagnoLetras[idElemento]+'%');
							}
						else
							elemento.style.fontSize = (tamagnoLetras[idElemento]+'%');	
					}
				}
			}
		}
		
		function dzResetFontSize(idElemento) {
			var elemento = document.all[idElemento];
			elemento.body.style.fontSize = '100';
			document.body.style.fontSize = '100';
		}	

/*Llamados a los JS*/

	var tamagnoLetras	= new Array(); 
	tamagnoLetras['contenido_int'] = 100;
