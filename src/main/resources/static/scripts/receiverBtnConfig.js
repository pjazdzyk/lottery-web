let minNumber = 1;
let maxNumber = 99;
let totalNumbers = 6;
let numberInputsClassSelector = ".number-input-cell";
let fillRandomBtnId = "#fillRandomBtn";
let clearBtnId = "#clearBtn";


// FILL RANDOM BUTTON CONFIG
function getRandomLotteryNumber(minNum, maxNum){
    let randomNumber = Math.floor(Math.random() * (maxNum - minNum + 1)) + minNum;
    if(randomNumber!=0){
        return randomNumber;
    } else{
        getRandomLotteryNumber();
    }
}

function generateRandomDistinctLotteryNumbers(){
    let distinctNumberArray = new Array();
    while(distinctNumberArray.length < totalNumbers){
        let generatedNumber = getRandomLotteryNumber(minNumber,maxNumber);
        if(!distinctNumberArray.includes(generatedNumber)){
            distinctNumberArray.push(generatedNumber);
        }
    }
    return distinctNumberArray;
}


function fillInputFieldsWithRandomNumbers(){
    let inputNodes = document.querySelectorAll(numberInputsClassSelector);
    let generatedNumbers = generateRandomDistinctLotteryNumbers();
    for (const node of inputNodes) {
        node.value = generatedNumbers.pop();
      }
}

function registerClickFillRandomButton(buttonId){
    let fillRandomButton = document.querySelector(buttonId);
    fillRandomButton.addEventListener("click", fillInputFieldsWithRandomNumbers);
}

// CLEAR BUTTON CONFIG
function clearField(inputField){
    inputField.value="";
}

function clearAllFields(){
    let nodeList = document.querySelectorAll(numberInputsClassSelector);
    for (const node of nodeList) {
        clearField(node);
      }
}

function registerClickClearButton(buttonId){
    let fillRandomButton = document.querySelector(buttonId);
    fillRandomButton.addEventListener("click", clearAllFields);
}

// CONTEXT CALLS
registerClickFillRandomButton(fillRandomBtnId);
registerClickClearButton(clearBtnId);