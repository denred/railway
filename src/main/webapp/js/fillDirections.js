function fillDirections(from, to) {
    document.getElementById("from").value = from;
    document.getElementById("to").value = to;
}

function invertDirections(){
    let to = document.getElementById("from").value;
    let from = document.getElementById("to").value;
    fillDirections(from, to);
}