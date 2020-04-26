/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function showNotification(text,id){
    if(text !== ""){
        document.getElementById(id).style.height = "20%";
        setTimeout(function(){closeNotification(id);},2000);
    }
}
function closeNotification(id){
    document.getElementById(id).style.height = "0%";
}

function checkValidMoney(minID, maxID, errorBox, searchButton){
    var txtmin = document.getElementById(minID).value;
    var txtmax = document.getElementById(maxID).value;
    var MaxNumber = Number.POSITIVE_INFINITY;
    if(txtmin === null || txtmin === ""){
        txtmin = "0";
    }
    if(txtmax === null || txtmax === ""){
        txtmax = MaxNumber;
    }
    var min = parseFloat(txtmin);
    var max = parseFloat(txtmax);
    if(min<0){
        min = 0;
        document.getElementById(minID).value = "";
    }
    if(max<0){
        max = parseFloat(MaxNumber);
        document.getElementById(maxID).value = "";
    }
    
    if(min <= max){
        document.getElementById(errorBox).style.visibility = "hidden";
        document.getElementById(searchButton).disabled = false;
        document.getElementById(searchButton).style.opacity = "1";
    }else{
        document.getElementById(errorBox).style.visibility = "visible";
        document.getElementById(searchButton).disabled = true;
        document.getElementById(searchButton).style.opacity = "0.6";
    }
}

function getMaxDateToday(minID, maxID, mintext, maxtext){
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();
     if(dd<10){
            dd='0'+dd;
        } 
        if(mm<10){
            mm='0'+mm;
        } 

    today = yyyy+'-'+mm+'-'+dd;
    document.getElementById(minID).setAttribute("max", today);
    document.getElementById(maxID).setAttribute("max", today);
    document.getElementById(maxID).setAttribute("placeholder", today);
    
    if(mintext === ""){
        document.getElementById(minID).value = "1753-01-01";
    }else{
        document.getElementById(minID).value = mintext;
    }
    if(maxtext === ""){
        document.getElementById(maxID).value = today;
    }else{
        document.getElementById(maxID).value = maxtext;
    }
}

function checkValidDate(minID, maxID, errorBox, searchButton){
    var txtMinDate = document.getElementById(minID).value;
    var txtMaxDate = document.getElementById(maxID).value;
    
    try {
        var minDate = new Date();
        var maxDate = new Date();
        
        if(txtMinDate === ""){
            document.getElementById(minID).value = "1753-01-01";
            minDate = "1753-01-01";
        }else{
            minDate = txtMinDate;
        }
        if(txtMaxDate === ""){
            document.getElementById(maxID).value = document.getElementById(maxID).max;
            maxDate = document.getElementById(maxID).max;
        }else{
            maxDate = txtMaxDate;
        }
        
        if(minDate <= maxDate){
            document.getElementById(errorBox).style.visibility = "hidden";
            document.getElementById(errorBox).innerHTML = "Invalid date range!";
            document.getElementById(searchButton).disabled = false;
            document.getElementById(searchButton).style.opacity = "1";
        }else{
            document.getElementById(errorBox).style.visibility = "visible";
            document.getElementById(searchButton).disabled = true;
            document.getElementById(searchButton).style.opacity = "0.6";
        }
    } catch (e) {
        document.getElementById(errorBox).innerHTML = "Invalid date format!";
        document.getElementById(errorBox).style.visibility = "visible";
    }

    
    
}


