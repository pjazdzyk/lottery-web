let minNumber = 1;
let maxNumber = 99;
let totalNumbers = 6;
let numberInputsClassSelector = ".number-input-cell";
let fillRandomBtnId = "#fillRandomBtn";
let clearBtnId = "#clearBtn";

import {
    clearField,
    registerClickAction
} from './sharedScripts.js';

// FILL RANDOM BUTTON CONFIG
function getRandomLotteryNumber(minNum, maxNum) {
    let randomNumber = Math.floor(Math.random() * (maxNum - minNum + 1)) + minNum;
    if (randomNumber !== 0) {
        return randomNumber;
    } else {
        getRandomLotteryNumber();
    }
}

function generateRandomDistinctLotteryNumbers() {
    let distinctNumberArray = [];
    while (distinctNumberArray.length < totalNumbers) {
        let generatedNumber = getRandomLotteryNumber(minNumber, maxNumber);
        if (!distinctNumberArray.includes(generatedNumber)) {
            distinctNumberArray.push(generatedNumber);
        }
    }
    return distinctNumberArray;
}

function fillInputFieldsWithRandomNumbersAction() {
    let inputNodes = document.querySelectorAll(numberInputsClassSelector);
    let generatedNumbers = generateRandomDistinctLotteryNumbers();
    for (const node of inputNodes) {
        node.value = generatedNumbers.pop();
    }
}

// CLEAR BUTTON CONFIG

function clearAllFieldsAction() {
    let nodeList = document.querySelectorAll(numberInputsClassSelector);
    for (const node of nodeList) {
        clearField(node);
    }
}


// CONTEXT CALLS
registerClickAction(fillRandomBtnId, fillInputFieldsWithRandomNumbersAction);
registerClickAction(clearBtnId, clearAllFieldsAction);