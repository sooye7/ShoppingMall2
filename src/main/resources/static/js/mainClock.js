var clockTarget = document.getElementById("clock");

function clock() {
    var currentDate = new Date();
    var month = currentDate.getMonth();
    var date = currentDate.getDate();
    var day = currentDate.getDay();
    var week = ['일', '월', '화', '수', '목', '금', '토'];
    var hours = currentDate.getHours();
    var minutes = currentDate.getMinutes();
    var seconds = currentDate.getSeconds();
    clockTarget.innerText = `${month+1}월 ${date}일 ${week[day]}요일\n` +
        `${hours < 10 ? `0${hours}` : hours}:${minutes < 10 ? `0${minutes }`  : minutes }:${seconds < 10 ? `0${seconds }`  : seconds }`;
}

function init() {
clock();
setInterval(clock, 1000);
}

init();