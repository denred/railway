const replaceVulnerableChars = (array) => {
    let lt = /</g;
    let gt = />/g;
    let ap = /'/g;
    let ic = /"/g;

    array.forEach(function (element) {
        element.value = element.value
            .replace(lt, "&lt;")
            .replace(gt, "&gt;")
            .replace(ap, "&#39;")
            .replace(ic, "&#34;");
    })
}

const xssProtectionListener = (emenent) => {
    emenent.addEventListener("mousedown", () => {
        const textInput = document.querySelectorAll('input[type="text"]');
        const passwordInput = document.querySelectorAll('input[type="password"]');
        replaceVulnerableChars(textInput);
        replaceVulnerableChars(passwordInput);
    }, false);
}


const submitButtons = document.querySelectorAll('.submit');


submitButtons.forEach(function (button) {
    xssProtectionListener(button);
})


window.onload = function () {
    document.getElementById('mylink').onclick = function () {
        document.getElementById('myform').submit();
        return false;
    };
};

const searchFocus = document.getElementsB('search-focus');
const keys = [
    { keyCode: 'AltLeft', isTriggered: false },
    { keyCode: 'ControlLeft', isTriggered: false },
];

window.addEventListener('keydown', (e) => {
    keys.forEach((obj) => {
        if (obj.keyCode === e.code) {
            obj.isTriggered = true;
        }
    });

    const shortcutTriggered = keys.filter((obj) => obj.isTriggered).length === keys.length;

    if (shortcutTriggered) {
        searchFocus.focus();
    }
});

window.addEventListener('keyup', (e) => {
    keys.forEach((obj) => {
        if (obj.keyCode === e.code) {
            obj.isTriggered = false;
        }
    });
});

// Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation')

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
})()
