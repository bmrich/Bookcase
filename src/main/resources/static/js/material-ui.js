let drawer = new mdc.drawer.MDCTemporaryDrawer(document.querySelector('.mdc-temporary-drawer'));
document.querySelector('.menu-btn').addEventListener('click', () => drawer.open = !drawer.open);

let menu = new mdc.menu.MDCSimpleMenu(document.querySelector('.mdc-simple-menu'));
document.querySelector('#logout-menu').addEventListener('click', () => menu.open = !menu.open);

mdc.textfield.MDCTextfield.attachTo(document.querySelector('.mdc-textfield'));