/**
 * Created by artur on 11.02.2018.
 */
function Computer(){
    /*
    * Массив в который сохраняем названные компьюетром города*/
     var archiveCities = [];

    this.addCity = function(newCity){
        archiveCities.push(newCity);
    };

    this.getArchiveCities=function(){
        return archiveCities;
    };

    this.isNewCity = function(newCity){
        if(archiveCities.indexOf(newCity)<0)
            return true;
        else
            return false;
    };

    /*
    * фукнция которая возвращает последнюю букву города, который назвал компьютер*/
    this.getLastLetter = function () {
        var city = document.getElementById("computerAnswer").value;
        if (city !== "") {
            if (city.split("")[city.length - 1] === "ь" || city.split("")[city.length - 1] === "ъ" ||
                city.split("")[city.length - 1] === "ы" || city.split("")[city.length - 1] === "й" ||
                city.split("")[city.length - 1] === "ё") {
                if (city.split("")[city.length - 2] === "й")
                    return city.split("")[city.length - 3].toUpperCase();
                return city.split("")[city.length - 2].toUpperCase();
            }
            return city.split("")[city.length - 1].toUpperCase();
        }
    };


    /*
    * функция, дающая ответ на город пользователя по последней букве*/
    this.giveAnswer = function (lastLetter){
        var city = cityBase.getCity(lastLetter);
        if(city!==undefined) {
            document.getElementById("computerAnswer").value = city;
            computer.addCity(city);
            geocode(city);
            return city;
        }
        else{
            alert("Вы выиграли, т.к. компьютер на смог найти город на букву "+user.getLastLetter());
            endGame("Вы выиграли");
        }
    }
}