/**
 * Created by artur on 06.02.2018.
 */
/*
 * Описываем класс User*/
function User() {
    var city;
    /*
     * Массив, в который сохраняем введеные пользователем города*/
    var archiveCities = [];
    this.addCity = function(newCity){
        archiveCities.push(newCity);
    };
    this.getArchiveCities=function(){
        return archiveCities;
    };
     function isNewCity(newCity){
        if(archiveCities.indexOf(newCity)<0)
            return computer.isNewCity(newCity);
        else
            return false;
    }

    /*Функция распознающая голосовой ввод*/
    this.recongnize = function (element) {
        // Создаем распознаватель
        var recognizer = new webkitSpeechRecognition();

        // Ставим опцию, чтобы распознавание началось ещё до того, как пользователь закончит говорить
        recognizer.interimResults = false;

        // Какой язык будем распознавать?
        recognizer.lang = 'ru-Ru';

        // Используем колбек для обработки результатов
        recognizer.onresult = function (event) {
            var result = event.results[0][0].transcript;
            console.log(result);
            if (result.split("")[result.length - 1] === " ")
                result = result.split(" ")[0];
            element.form.userCity.value = result;
        };
        recognizer.start();
    };

    this.getLastLetter = function () {
        if (city.split("")[city.length - 1] === "ь" || city.split("")[city.length - 1] === "ъ" ||
            city.split("")[city.length - 1] === "ы" || city.split("")[city.length - 1] === "й" ||
            city.split("")[city.length - 1] === "ё") {
            if (city.split("")[city.length - 2] === "й")
                return city.split("")[city.length - 3].toUpperCase();
            return city.split("")[city.length - 2].toUpperCase();
        }
        return city.split("")[city.length - 1].toUpperCase();
    };

    /*Функция обрабатывает введенный пользоватлем город*/
    this.inputCity = function () {
        city = document.getElementById('userCity').value;
        city = city.charAt(0).toUpperCase() + city.substring(1).toLowerCase();
        if (computer.getLastLetter() !== city.split("")[0] && computer.getLastLetter() !== undefined) {
            alert("Введите город на букву " + computer.getLastLetter());
            return false;
        }
        else {
            if (!isNewCity(city))
                alert("Город " + city + " уже был");
            else {
                var rightCity = cityBase.findCity(city);
                if (!rightCity) {
                    debugger;
                    alert("Неправильно введен город");
                    return false;
                }
                else {
                    return city;
                }
            }
        }
    }
}

