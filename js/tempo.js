
$(document).ready(function(){
    let apiKey = "44b81114cf9fdb8f0d8bed2f3e7e6f30";

    var todayDate = Tempo.date().fullDateToday
    
    var latitude = Tempo.getLocation().latitude
    var longitude = Tempo.getLocation().longitude
    var temperature = Tempo.getWeather(latitude,longitude,apiKey).temperature
    var humidity = Tempo.getWeather(latitude, longitude, apiKey).humidity

    $('.date').text(todayDate)
    $('.humidity').text(humidity)
    $('.grades').text(parseInt(temperature))
    
})