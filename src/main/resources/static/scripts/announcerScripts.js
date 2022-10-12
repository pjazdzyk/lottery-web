let clearBtnId = "#anouncerClearBtn";
let uuidFormId = "#requestUuidForm";

import {
    clearField,
    registerClickAction
} from './sharedScripts.js';

function clearUuidFormAction(){
    let uuidTextField = document.querySelector(uuidFormId);
    clearField(uuidTextField);
}

// CONTEXT CALLS
registerClickAction(clearBtnId, clearUuidFormAction);