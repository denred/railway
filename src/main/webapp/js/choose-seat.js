let count = 0;

document.addEventListener('click', function (e) {
    if (e.target.classList.contains('seat-free')) {
        let style = getComputedStyle(e.target);
        let elementColor = style.color;
        let id = e.target.attributes['aria-details'].value;
        if (elementColor === 'rgb(255, 255, 255)') {
            e.target.style.color = 'green';
            e.target.style.backgroundColor = 'white';
            let input = document.getElementById(id);
            input.removeAttribute('checked');
            disabledButtonNext(true);
        }
        if (elementColor === 'rgb(0, 128, 0)') {
            e.target.style.color = 'white';
            e.target.style.backgroundColor = 'blue';
            let formTag = document.getElementById("seatsId");
            let input = document.createElement("input");
            input.type = 'checkbox';
            input.name = 'seats_id';
            input.setAttribute('hidden', '');
            input.setAttribute('checked', '');
            input.setAttribute("id", id);
            input.value = id;
            formTag.append(input);
            disabledButtonNext(false);
        }
    }
});


function disabledButtonNext(state) {
    let button = document.getElementById('button_next');
    if (!state) {
        count++;
    } else {
        count--;
    }
    button.disabled = count === 0;
}



