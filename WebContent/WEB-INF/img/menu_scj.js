/*
Funcion para Menu colapsable
Puede usar Tabs
*/

var cspbFO=true;
var cspbTL=0;
var cspbTD=0;
var cspbGDH=0;
var cspbGD;
var cspbEIA=false;

function IMMenu(llave, cantidad) {
	this.llave		= llave;
	this.cantidad	= cantidad;
	this.autoClose	= true;
	this.closeAll	= true;
	this.colaspseHeight = true;
	var iDelay = 10 ;
	var sDisplayTimer = null;
	var oLastItem = true;
	var iNSWidth = 100;
	
	this.useTabs = false;
	this.classTabsActive = "activate";
	this.classTabsInactive = "off";
	this.nameTab = "main";
	
	var enUso = false;
	var entrada		= new Array();
	var content		= new Array();
	
	isOPERA = (navigator.userAgent.indexOf('Opera') >= 0)? true : false;
	isIE    = (document.all && !isOPERA)? true : false;
	isDOM   = (document.getElementById && !isIE)? true : false;
	
	this.initArray = function() {
		for (x=0; x < this.cantidad; x++) {
			entrada [this.llave + "_" + x] = true;
		}
	}
	
	this.make_menu = function(id){
		if(cspbEIA){
			return;
		}
		if(this.closeAll) {
			this.hide_all();
		}
		if (entrada[this.llave + "_" + id]) {
			this.show (id);
			for (x=0; x < this.cantidad; x++){
				entrada [this.llave + "_" + x] = true;
			}
			if(this.autoClose) {
				entrada[this.llave + "_" + id] = false;
			}
		}else{
			if(this.autoClose) {
				this.hide(id);
			}
			entrada[this.llave + "_" + id]=true;
		} 
	}	
	
	this.hide_all = function() {
		for (i=0; i<this.cantidad;i++){
			this.hide(i);
		}
	}
	
	this.hide = function(id){
		if (isDOM) {
			el	= document.getElementById(this.llave +  "_" + id);
			if(this.useTabs) mainTab = document.getElementById(this.nameTab + this.llave +  "_" + id);
		} else if (isIE) {
			el = document.all[this.llave + "_" + id];
			if(this.useTabs) mainTab = document.all[this.nameTab + this.llave +  "_" + id];
		}
		cspbGD = el;
		try {
			if(this.useTabs && mainTab) mainTab.className=this.classTabsInactive;
		}
		catch(e){}
		el.style.display='none';
		return;
		
		if(this.colaspseHeight)
		{
			cspbGDH = el.offsetHeight;
		}
		else
		{
			cspbGDH = '';//el.offsetWidth;
		}
		objMenu = this;
		cspbEC(0);
	}
	
	
	this.show = function(id){
		
		if (isDOM) {
			el	= document.getElementById(this.llave +  "_" + id);
			if(this.useTabs) mainTab = document.getElementById(this.nameTab + this.llave +  "_" + id);
		} else if (isIE) {
			el = document.all[this.llave + "_" + id];
			if(this.useTabs) mainTab = document.all[this.nameTab + this.llave +  "_" + id];
		}
		try {
			if(this.useTabs && mainTab) mainTab.className = this.classTabsActive;
		}
		catch(e){}
		el.style.display = "block";
		cspbGD=el;
		return;
		if(this.colaspseHeight)
		{
			cspbGDH = el.offsetHeight;
			el.style.height=1;
		}
		else
		{
			cspbGDH = el.offsetWidth;
			el.style.width=1;
		}
		objMenu = this;
		cspbEO(cspbGDH);
	}

	this.stopTimer = function () {
		clearTimeout(sDisplayTimer)
	}

	this.startTimer = function () {
	  this.stopTimer()
	  objMenu = this;
	  sDisplayTimer = setTimeout('callHideItem()',iDelay)
	}

	this.hideItem = function () {
		if (oLastItem) {
			this.hide_all();
		}
	}
}
var objMenu;
function callHideItem() {
	objMenu.hideItem();
}


function cspbEO(l){
	var RES=1;
	cspbEIA=true;
	cspbTL=l;

	if(cspbTL==0){
		if(objMenu.colaspseHeight)
		{
			cspbGD.style.height=cspbGDH;
		}
		else
		{
			cspbGD.style.width=cspbGDH;	
		}
		cspbEIA=false;
	} else{
		if(cspbTL<=RES){
			cspbTD=1;
		}else{
			cspbTD=parseInt(cspbTL/RES);
		}
		if(cspbGDH!=cspbTL){
			if(objMenu.colaspseHeight)
			{
				cspbGD.style.height=cspbGDH-cspbTL;
			}
			else
			{
				cspbGD.style.width=cspbGDH-cspbTL;
			}
		}
		setTimeout('cspbEO(cspbTL-cspbTD);',20);
	}
}

function cspbEC(l){
	var RES=6;
	cspbEIA=true;
	cspbTL=l;
	if(cspbGDH==cspbTL || cspbGDH<cspbTL){
		cspbGD.style.display='none';
		if(objMenu.colaspseHeight)
		{
			cspbGD.style.height=cspbGDH;
		}
		else
		{
			cspbGD.style.width=cspbGDH;
		}
		cspbEIA=false;
	}else{
		if((cspbGDH-cspbTL)<=RES){
			cspbTD=1;
		}else{
			cspbTD=parseInt((cspbGDH-cspbTL)/RES);
		}
		if(objMenu.colaspseHeight)
		{
			cspbGD.style.height = cspbGDH - cspbTL;
		}
		else
		{
			cspbGD.style.width = cspbGDH - cspbTL;
		}
		setTimeout('cspbEC(cspbTL+cspbTD);',10);
	}
}
function MakeMenu(llave, cantidad, colaspseHeight, initArray) {
	try
	{
		immenu = new IMMenu(llave, cantidad);
		if(initArray)
		{
			immenu.initArray();
		}
		immenu.colaspseHeight = colaspseHeight;
		return immenu;
	}
	catch(e)
	{
		return false;
	}
}



window.onerror = function()
{
    return true;        
}



/*
Funcion para menu desplegable, se despliega
sobre cualquier elemento (flash o de formularios)
*/

function Browser() {

  var ua, s, i;

  this.isIE    = false;  // Internet Explorer
  this.isOP    = false;  // Opera
  this.isNS    = false;  // Netscape
  this.version = null;

  ua = navigator.userAgent;

  s = "Opera";
  if ((i = ua.indexOf(s)) >= 0) {
    this.isOP = true;
    this.version = parseFloat(ua.substr(i + s.length));
    return;
  }

  s = "Netscape6/";
  if ((i = ua.indexOf(s)) >= 0) {
    this.isNS = true;
    this.version = parseFloat(ua.substr(i + s.length));
    return;
  }

  // Treat any other "Gecko" browser as Netscape 6.1.

  s = "Gecko";
  if ((i = ua.indexOf(s)) >= 0) {
    this.isNS = true;
    this.version = 6.1;
    return;
  }

  s = "MSIE";
  if ((i = ua.indexOf(s))) {
    this.isIE = true;
    this.version = parseFloat(ua.substr(i + s.length));
    return;
  }
}

var browser = new Browser();

// Code for handling the menu bar and active button.

var activeButton = null;


function buttonClick(event, menuId) {

  var button;

  // Get the target button element.

  if (browser.isIE)
    button = window.event.srcElement;
  else
    button = event.currentTarget;

  // Blur focus from the link to remove that annoying outline.

  button.blur();


  if (button.menu == null) {
    button.menu = document.getElementById(menuId);
    if (button.menu.isInitialized == null)
      menuInit(button.menu);
  }


  if (button.onmouseout == null)
    button.onmouseout = buttonOrMenuMouseout;


  if (button == activeButton)
    return false;


  if (activeButton != null)
    resetButton(activeButton);


  if (button != activeButton) {
    depressButton(button);
    activeButton = button;
  }
  else
    activeButton = null;

  return false;
}

function buttonMouseover(event, menuId) {

  var button;


  if (activeButton == null) {
    buttonClick(event, menuId);
    return;
  }


  if (browser.isIE)
    button = window.event.srcElement;
  else
    button = event.currentTarget;


  if (activeButton != null && activeButton != button)
    buttonClick(event, menuId);
}

function depressButton(button) {

  var x, y;

  button.className += " menuButtonActive";


  if (button.onmouseout == null)
    button.onmouseout = buttonOrMenuMouseout;
  if (button.menu.onmouseout == null)
    button.menu.onmouseout = buttonOrMenuMouseout;


  x = getPageOffsetLeft(button);
  y = getPageOffsetTop(button) + button.offsetHeight;

  // For IE, adjust position.

  if (browser.isIE) {
    x += button.offsetParent.clientLeft;
    y += button.offsetParent.clientTop;
  }

  button.menu.style.left = x + "px";
  button.menu.style.top  = y + "px";
  button.menu.style.visibility = "visible";

  // For IE; size, position and show the menu's IFRAME as well.

  if (button.menu.iframeEl != null)
  {
    button.menu.iframeEl.style.left = button.menu.style.left;
    button.menu.iframeEl.style.top  = button.menu.style.top;
    button.menu.iframeEl.style.width  = button.menu.offsetWidth + "px";
    button.menu.iframeEl.style.height = button.menu.offsetHeight + "px";
    button.menu.iframeEl.style.display = "";
  }
}

function resetButton(button) {

  // Restore the button's style class.

  removeClassName(button, "menuButtonActive");

  // Hide the button's menu, first closing any sub menus.

  if (button.menu != null) {
    closeSubMenu(button.menu);
    button.menu.style.visibility = "hidden";

    // For IE, hide menu's IFRAME as well.

    if (button.menu.iframeEl != null)
      button.menu.iframeEl.style.display = "none";
  }
}

// Code to handle the menus and sub menus.


function menuMouseover(event) {

  var menu;

  // Find the target menu element.

  if (browser.isIE)
    menu = getContainerWith(window.event.srcElement, "DIV", "menu");
  else
    menu = event.currentTarget;

  // Close any active sub menu.

  if (menu.activeItem != null)
    closeSubMenu(menu);
}

function menuItemMouseover(event, menuId) {

  var item, menu, x, y;

  // Find the target item element and its parent menu element.

  if (browser.isIE)
    item = getContainerWith(window.event.srcElement, "A", "");//menuItem
  else
    item = event.currentTarget;
  menu = getContainerWith(item, "DIV", "menu");//menu

  // Close any active sub menu and mark this one as active.

  if (menu.activeItem != null)
    closeSubMenu(menu);
  menu.activeItem = item;

  // Highlight the item element.

  item.className += " menuItemHighlight";

  // Initialize the sub menu, if not already done.

  if (item.subMenu == null) {
    item.subMenu = document.getElementById(menuId);
    if (item.subMenu.isInitialized == null)
      menuInit(item.subMenu);
  }


  if (item.subMenu.onmouseout == null)
    item.subMenu.onmouseout = buttonOrMenuMouseout;


  x = getPageOffsetLeft(item) + item.offsetWidth;
  y = getPageOffsetTop(item);


  var maxX, maxY;

  if (browser.isIE) {
    maxX = Math.max(document.documentElement.scrollLeft, document.body.scrollLeft) +
      (document.documentElement.clientWidth != 0 ? document.documentElement.clientWidth : document.body.clientWidth);
    maxY = Math.max(document.documentElement.scrollTop, document.body.scrollTop) +
      (document.documentElement.clientHeight != 0 ? document.documentElement.clientHeight : document.body.clientHeight);
  }
  if (browser.isOP) {
    maxX = document.documentElement.scrollLeft + window.innerWidth;
    maxY = document.documentElement.scrollTop  + window.innerHeight;
  }
  if (browser.isNS) {
    maxX = window.scrollX + window.innerWidth;
    maxY = window.scrollY + window.innerHeight;
  }
  maxX -= item.subMenu.offsetWidth;
  maxY -= item.subMenu.offsetHeight;

  if (x > maxX)
    x = Math.max(0, x - item.offsetWidth - item.subMenu.offsetWidth
      + (menu.offsetWidth - item.offsetWidth));
  y = Math.max(0, Math.min(y, maxY));

  // Position and show the sub menu.

  item.subMenu.style.left       = x + "px";
  item.subMenu.style.top        = y + "px";
  item.subMenu.style.visibility = "visible";

  // For IE; size, position and display the menu's IFRAME as well.

  if (item.subMenu.iframeEl != null)
  {
    item.subMenu.iframeEl.style.left    = item.subMenu.style.left;
    item.subMenu.iframeEl.style.top     = item.subMenu.style.top;
    item.subMenu.iframeEl.style.width   = item.subMenu.offsetWidth + "px";
    item.subMenu.iframeEl.style.height  = item.subMenu.offsetHeight + "px";
    item.subMenu.iframeEl.style.display = "";
  }

  // Stop the event from bubbling.

  if (browser.isIE)
    window.event.cancelBubble = true;
  else
    event.stopPropagation();
}

function closeSubMenu(menu) {

  if (menu == null || menu.activeItem == null)
    return;

  // Recursively close any sub menus.

  if (menu.activeItem.subMenu != null) {
    closeSubMenu(menu.activeItem.subMenu);
    menu.activeItem.subMenu.style.visibility = "hidden";

    // For IE, hide the sub menu's IFRAME as well.

    if (menu.activeItem.subMenu.iframeEl != null)
      menu.activeItem.subMenu.iframeEl.style.display = "none";

    menu.activeItem.subMenu = null;
  }

  // Deactivate the active menu item.

  removeClassName(menu.activeItem, "menuItemHighlight");
  menu.activeItem = null;
}

// [MODIFIED] Added for activate/deactivate on mouseover. Handler for mouseout
// event on buttons and menus.

function buttonOrMenuMouseout(event) {

  var el;

  // If there is no active button, exit.

  if (activeButton == null)
    return;

  // Find the element the mouse is moving to.

  if (browser.isIE)
    el = window.event.toElement;
  else if (event.relatedTarget != null)
      el = (event.relatedTarget.tagName ? event.relatedTarget : event.relatedTarget.parentNode);


  if (getContainerWith(el, "DIV", "menu") == null) {
    resetButton(activeButton);
    activeButton = null;
  }
}


function menuInit(menu) {

  var itemList, spanList;
  var textEl, arrowEl;
  var itemWidth;
  var w, dw;
  var i, j;

  // For IE, replace arrow characters.

  if (browser.isIE) {
    menu.style.lineHeight = "2.5ex";
    spanList = menu.getElementsByTagName("SPAN");
    for (i = 0; i < spanList.length; i++)
      if (hasClassName(spanList[i], "menuItemArrow")) {
        spanList[i].style.fontFamily = "Webdings";
        spanList[i].firstChild.nodeValue = "4";
      }
  }

  // Find the width of a menu item.

  itemList = menu.getElementsByTagName("A");
  if (itemList.length > 0)
    itemWidth = itemList[0].offsetWidth;
  else
    return;

  for (i = 0; i < itemList.length; i++) {
    spanList = itemList[i].getElementsByTagName("SPAN");
    textEl  = null;
    arrowEl = null;
    for (j = 0; j < spanList.length; j++) {
      if (hasClassName(spanList[j], "menuItemText"))
        textEl = spanList[j];
      if (hasClassName(spanList[j], "menuItemArrow"))
        arrowEl = spanList[j];
    }
    if (textEl != null && arrowEl != null) {
      textEl.style.paddingRight = (itemWidth 
        - (textEl.offsetWidth + arrowEl.offsetWidth)) + "px";
      // For Opera, remove the negative right margin to fix a display bug.
      if (browser.isOP)
        arrowEl.style.marginRight = "0px";
    }
  }


  if (browser.isIE) {
    w = itemList[0].offsetWidth;
    itemList[0].style.width = w + "px";
    dw = itemList[0].offsetWidth - w;
    w -= dw;
    itemList[0].style.width = w + "px";
  }


  if (browser.isIE) {
    var iframeEl = document.createElement("IFRAME");
    iframeEl.frameBorder = 0;
    iframeEl.src = "javascript:false;";
    iframeEl.style.display = "none";
    iframeEl.style.position = "absolute";
    iframeEl.style.filter = "progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)";
    menu.iframeEl = menu.parentNode.insertBefore(iframeEl, menu);
  }

  // Mark menu as initialized.

  menu.isInitialized = true;
}


function getContainerWith(node, tagName, className) {


  while (node != null) {
    if (node.tagName != null && node.tagName == tagName &&
        hasClassName(node, className))
      return node;
    node = node.parentNode;
  }

  return node;
}

function hasClassName(el, name) {

  var i, list;

  list = el.className.split(" ");
  for (i = 0; i < list.length; i++)
    if (list[i] == name)
      return true;

  return false;
}

function removeClassName(el, name) {

  var i, curList, newList;

  if (el.className == null)
    return;


  newList = new Array();
  curList = el.className.split(" ");
  for (i = 0; i < curList.length; i++)
    if (curList[i] != name)
      newList.push(curList[i]);
  el.className = newList.join(" ");
}

function getPageOffsetLeft(el) {

  var x;


  x = el.offsetLeft;
  if (el.offsetParent != null)
    x += getPageOffsetLeft(el.offsetParent);

  return x;
}

function getPageOffsetTop(el) {

  var y;


  y = el.offsetTop;
  if (el.offsetParent != null)
    y += getPageOffsetTop(el.offsetParent);

  return y;
}