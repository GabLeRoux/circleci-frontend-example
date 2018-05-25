'use strict';

window.calculator = (function () {
    return {
        init: init
    };

    function getIntById(id) {
        return parseInt(document.getElementById(id).value, 10);
    }

    function calculate() {
        let sum = getIntById('x') + getIntById('y');
        if (isNaN(sum)) {
            document.getElementById('result').innerHTML = "0";
        } else {
            document.getElementById('result').innerHTML = sum;
        }
    }

    function init() {
        document.getElementById('add').addEventListener('click', calculate);
    }
})();