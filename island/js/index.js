/**
 * Created by artur on 11.02.2018.

Список городо заранее сохранен, и пользователь в процессе игры в качестве ответа будет получать город по одну,
данным образом иммитируя процесс реальной игры с человеком.
 */
var cityBase = new CityBase();
var user = new User();
var computer = new Computer();
var timerId;

function inputCity(){
    var city = user.inputCity();
    if(!city) {
        return false;
    }
    else{
        clearTimeout(timerId);
        geocode(city);
        user.addCity(city);
        console.log("Ответ компьютера");
        computer.giveAnswer(user.getLastLetter());
        document.getElementById("userCity").value="";
        timerId = setTimeout(function () {
            document.getElementById("userCity").disabled=true;
            alert("Конец игры, вы проиграли");
            endGame("Компьютер выиграл");
        },120000);
    }
}

function endGame(winner){
    var userAnswer = user.getArchiveCities().toString();
    var computerAnswer = computer.getArchiveCities().toString();
    document.getElementById("game").innerHTML ="<h1>"+winner+ "</h1> <br><p> Города пользователя: " + userAnswer+"</p><br>"+
        "<p>Города компьютера: " + computerAnswer+"</p>";
}

function speak(object) {
    user.recongnize(object);
}

