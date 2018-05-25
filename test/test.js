'use strict';

describe('Calculator', function () {

    var controls = {
        get result() {
            return document.getElementById('result').innerHTML;
        },
        get x() {
            return document.getElementById('x').value;
        },
        set x(val) {
            document.getElementById('x').value = val;
        },
        get y() {
            return document.getElementById('y').value;
        },
        set y(val) {
            document.getElementById('y').value = val;
        },
        clickAdd: function () {
            document.getElementById('add').click();
        }
    };

    beforeEach(function () {
        fixture.base = 'test';
        fixture.load('calculator.fixture.html');

        window.calculator.init();
    });

    afterEach(function () {
        fixture.cleanup();
    });

    it('should calculate 3 for 1 + 2', function () {
        controls.x = 1;
        controls.y = 2;
        controls.clickAdd();
        controls.result.should.equal('3');
    });

    it('should calculate zero for invalid x value', function () {
        controls.x = 'hello';
        controls.y = 2;
        controls.clickAdd();
        controls.result.should.equal('0');
    });

    it('should calculate zero for invalid y value', function () {
        controls.x = 1;
        controls.y = 'goodbye';
        controls.clickAdd();
        controls.result.should.equal('0');
    });

});