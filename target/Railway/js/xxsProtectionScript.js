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