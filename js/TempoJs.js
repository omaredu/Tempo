
let Tempo = class {
    static date() {
        var d = new Date()
        var year = d.getFullYear()

        var weekday = new Array(7)
        weekday[0] = "Sunday"
        weekday[1] = "Monday"
        weekday[2] = "Tuesday"
        weekday[3] = "Wednesday"
        weekday[4] = "Thursday"
        weekday[5] = "Friday"
        weekday[6] = "Saturday"

        var monthName = new Array(12)
        monthName[0] = "January"
        monthName[1] = "February"
        monthName[2] = "March"
        monthName[3] = "April"
        monthName[4] = "May"
        monthName[5] = "June"
        monthName[6] = "July"
        monthName[7] = "August"
        monthName[8] = "September"
        monthName[9] = "October"
        monthName[10] = "November"
        monthName[11] = "December"


        var weekdayFull = weekday[d.getDay()]
        var month = monthName[d.getMonth()]
        var monthDay = d.getDate()

        var fullDate = weekdayFull + ", " + month + " " + monthDay + ", " + year

        return {
            fullDateToday : fullDate,
            monthToday : month,
            monthDay : monthDay,
            yearToday : year
        }
    }

    static getLocation(){
        if (navigator.geolocation){
            navigator.geolocation.getCurrentPosition(function(position){
                var lat = position.coords.latitude;
                localStorage.setItem('lat', lat)
                var lon = position.coords.longitude;
                localStorage.setItem('lon', lon)
            })
            return {
                geolocation: true,
                latitude: localStorage.getItem('lat'),
                longitude: localStorage.getItem('lon')
            }
        }
        else {
            errorLog("Geolocation is not supported by this browser.")
            return {
                geolocation : false,
                latitude : null,
                longitude : null 
            }
        }
    }

    static getWeather(latitude, longitude, apiKey){
        $.get("http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey +"&units=metric", function (data) {                
            var jsonDefine = JSON.stringify(data)
            var json = JSON.parse(jsonDefine)
            localStorage.setItem('temp', json.main.temp)
            localStorage.setItem('hum', json.main.humidity)
        })
        return {
            humidity: localStorage.getItem('hum'),
            temperature: localStorage.getItem('temp')
        }
    }
}

function errorLog(text){
    console.log("Error: " + text)
}
