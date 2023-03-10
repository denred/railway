const cc_name = document.getElementById('cc_name');
const cc_numbers = document.getElementsByClassName('card-number');
const cc_cvv = document.getElementById('cc_cvv');
const writeOffBalance = document.getElementById('writeOffBalance');
const payButton = document.getElementById('payment-button');

cc_name.addEventListener('input', function () {
    checkFormValidation();
});

cc_cvv.addEventListener('input', function () {
    checkFormValidation();
});

for (let i = 0; i < cc_numbers.length; i++) {
    cc_numbers[i].addEventListener('input', function () {
        checkFormValidation();
    });
}

writeOffBalance.addEventListener('input', function () {
    if (writeOffBalance.checked) {
        setDisabledStatus(true);
        clearErrorMessage();
    } else {
        removeDisabledStatus();
        checkFormValidation();
    }
});

payButton.addEventListener('click', function () {
    if (writeOffBalance.checked || checkErrorMessage()) {
        document.getElementById('confirm-order').submit();
    }
});

function setDisabledStatus(status) {
    const exp_month = document.getElementById('expiry-month');
    const exp_year = document.getElementById('expiry-year');
    cc_name.setAttribute('disabled', status);
    cc_cvv.setAttribute('disabled', status);
    for (let i = 0; i < cc_numbers.length; i++) {
        cc_numbers[i].setAttribute('disabled', status);
    }
    exp_month.setAttribute('disabled', status);
    exp_year.setAttribute('disabled', status);
}

function removeDisabledStatus() {
    cc_name.removeAttribute('disabled');
    cc_cvv.removeAttribute('disabled');
    for (let i = 0; i < cc_numbers.length; i++) {
        cc_numbers[i].removeAttribute('disabled');
    }
    document.getElementById('expiry-month').removeAttribute('disabled');
    document.getElementById('expiry-year').removeAttribute('disabled');
}

function validateForm(inputValue, pattern, errorMessage, errorId) {
    const errorElement = document.getElementById(errorId);
    if (!pattern.test(inputValue) || inputValue === '') {
        errorElement.innerHTML = errorMessage;
        errorElement.style.color = 'red';
    } else {
        errorElement.innerHTML = "";
    }
}

function clearErrorMessage() {
    document.getElementById('cc_name_error').innerHTML = "";
    document.getElementById('cc_cvv_error').innerHTML = "";
    document.getElementById('cc_number_error').innerHTML = "";
}

function checkErrorMessage() {
    checkFormValidation();
    const err1 = document.getElementById('cc_name_error').textContent;
    const err2 = document.getElementById('cc_cvv_error').textContent;
    const err3 = document.getElementById('cc_number_error').textContent;
    return err1 === '' && err2 === '' && err3 === '';
}

function checkFormValidation() {
    validateForm(cc_name.value, /^\w+\s+\w+.*$/, "Please enter a valid name", "cc_name_error");
    validateForm(cc_cvv.value, /\d{3}/, "Please enter a valid cvv code", "cc_cvv_error");

    for (let j = 0; j < cc_numbers.length; j++) {
        validateForm(cc_numbers[j].value, /\d{4}/, "Please enter a valid card number", "cc_number_error");
    }
}


