const setCustomValidity = (inputField, errorMessage, errorElement) => {
    if (!inputField.checkValidity()) {
        inputField.setCustomValidity(errorMessage);
        errorElement.textContent = errorMessage;
        errorElement.style.display = 'block';
    } else {
        inputField.setCustomValidity('');
        errorElement.textContent = '';
        errorElement.style.display = 'none';
    }
};

const inputFieldTrain = document.querySelector('input[name="train_number"]');
const errorElementTrain = inputFieldTrain.nextElementSibling;
const errorMessageTrain = inputFieldTrain.dataset.error;

inputFieldTrain.addEventListener('input', function() {
    setCustomValidity(inputFieldTrain, errorMessageTrain, errorElementTrain);
});

const inputFieldFilter = document.querySelector('input[name="trainFilter"]');
const errorElementFilter = inputFieldFilter.nextElementSibling;
const errorMessageFilter = inputFieldFilter.dataset.error;

inputFieldFilter.addEventListener('input', function() {
    setCustomValidity(inputFieldFilter, errorMessageFilter, errorElementFilter);
});
