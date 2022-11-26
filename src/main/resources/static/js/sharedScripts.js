export function clearField(inputField){
    inputField.value="";
}

export function registerClickAction(buttonId, action){
    let fillRandomButton = document.querySelector(buttonId);
    fillRandomButton.addEventListener("click", action);
}
