var myMap;

// Дождёмся загрузки API и готовности DOM.
ymaps.ready(init);
function init () {
    // Создание экземпляра карты и его привязка к контейнеру с
    // заданным id ("map.js").
    myMap = new ymaps.Map('map', {
        // При инициализации карты обязательно нужно указать
        // её центр и коэффициент масштабирования.
        center:[55.76, 37.64], // Москва
        zoom:5
    });
}


/**/
function geocode(city) {
    var myGeocoder = ymaps.geocode(city, {kind:'locality',results:1});
    myGeocoder.then(
        function (res) {
            if(res.geoObjects.getLength()!==0) {
                myMap.geoObjects.add(res.geoObjects);

            }
            else
                console.log("Не могу найти город на карте");
        },
        function (err) {
            alert("Ошибка");
        }
    );
}