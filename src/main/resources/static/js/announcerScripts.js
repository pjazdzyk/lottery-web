let clearBtnId = "#announcerClearBtn";
let uuidFormId = "#uuidForm";

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