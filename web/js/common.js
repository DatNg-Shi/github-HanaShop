/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function deleteRecord(nameKey) {
    var doIt = confirm("Do you want to delete the record?");
    if (doIt) {
        var f = document.form;
        f.method = "post";
        f.action = '/J3.L.P0005_HanaShop/updateFood?&pk=' + nameKey + '&btAction=Delete';
        f.submit();
    } else {

    }
}

function myFunction(status, items) {
    var doIt = confirm("Are you sure!!!");
    if (doIt) {
        var f = document.form;
        f.method = "post";
        f.action = '/J3.L.P0005_HanaShop/multipleChoice?&txtStatusFood=' + status + '&chkItem=' + items;
        f.submit();
    } else {

    }
}

function deleteItemsCart(checkBox) {
    var doIt = confirm("Delete Items From Cart!!!");
    if (doIt) {
        var f = document.form;
        f.method = "post";
        f.action = '/J3.L.P0005_HanaShop/processCart?&chkItem=' + checkBox + '&btAction=Delete';
        f.submit();
    } else {

    }
}

function checkValidMoney(minID, maxID, errorBox, searchButton) {
    var txtmin = document.getElementById(minID).value;
    var txtmax = document.getElementById(maxID).value;
    var MaxNumber = Number.POSITIVE_INFINITY;
    if (txtmin === null || txtmin === "") {
        txtmin = "0";
    }
    if (txtmax === null || txtmax === "") {
        txtmax = MaxNumber;
    }
    var min = parseFloat(txtmin);
    var max = parseFloat(txtmax);
    if (min < 0) {
        min = 0;
        document.getElementById(minID).value = "";
    }
    if (max < 0) {
        max = parseFloat(MaxNumber);
        document.getElementById(maxID).value = "";
    }

    if (min <= max) {
        document.getElementById(errorBox).style.visibility = "hidden";
        document.getElementById(searchButton).disabled = false;
        document.getElementById(searchButton).style.opacity = "1";
    } else {
        document.getElementById(errorBox).style.visibility = "visible";
        document.getElementById(searchButton).disabled = true;
        document.getElementById(searchButton).style.opacity = "0.6";
    }
}

function decrementValue(txtQuantity, txtPrice) {
    var quan = parseInt(txtQuantity, 10);
    var prices = parseFloat(txtPrice);
    if (quan > 1) {

        quan = quan - 1;
        document.getElementById("amount").value = quan;
        var total = quan * prices;
        document.getElementById("total").innerHTML = total;

    }
}

function incrementValue(txtQuantity, txtPrice) {
    var quan = parseInt(txtQuantity, 10);
    var prices = parseFloat(txtPrice);
    quan = quan + 1;
    document.getElementById("amount").value = quan;
    var total = quan * prices;
    document.getElementById("total").innerHTML = total;
}

function changeValueOnInput(txtQuantity, txtPrice) {
    var quan = parseInt(txtQuantity, 10);
    var prices = parseFloat(txtPrice);
    if (quan > 0 || txtQuantity === "") {
        if (txtQuantity === "") {
            quan = 0;
        }
        var total = quan * prices;
        document.getElementById("total").innerHTML = total;
    } else {
        document.getElementById("amount").value = 1;
        document.getElementById("total").innerHTML = prices;
    }
}



function decrementValueCS(AmountID, prevAmountID, totalID, UpdateAmountID, txtPrice, itemID, itemname) {
    var txtAmount = document.getElementById(AmountID).value;

    var amount = parseInt(txtAmount);
    var price = parseFloat(txtPrice);
    if (amount > 1) {
        var total = parseFloat(document.getElementById("totalAllItem").value) - amount * price;
        amount = amount - 1;

        document.getElementById(totalID).innerHTML = (amount * price);
        document.getElementById(UpdateAmountID).value = itemID + ";;;;;" + itemname + ";;;;;" + amount;
        document.getElementById("totalAllItem").value = (total + amount * price).toFixed(1);
        document.getElementById(prevAmountID).value = amount;
        document.getElementById(AmountID).value = amount;
    }
}
function incrementValueCS(AmountID, prevAmountID, totalID, UpdateAmountID, txtPrice, itemID, itemname) {
    var txtAmount = document.getElementById(AmountID).value;

    var amount = parseInt(txtAmount);
    var price = parseFloat(txtPrice);
    var total = parseFloat(document.getElementById("totalAllItem").value) - amount * price;
    amount = amount + 1;

    document.getElementById(totalID).innerHTML = (amount * price).toFixed(1);
    document.getElementById(UpdateAmountID).value = itemID + ";;;;;" + itemname + ";;;;;" + amount;
    document.getElementById("totalAllItem").value = (total + amount * price).toFixed(1);
    document.getElementById(prevAmountID).value = amount;
    document.getElementById(AmountID).value = amount;
}

function changeValueOnInputCS(AmountID, prevAmountID, totalID, UpdateAmountID, txtPrice, itemID, itemname) {
    var txtPrevAmount = document.getElementById(prevAmountID).value;
    var txtAmount = document.getElementById(AmountID).value;
    var prevAmount = parseInt(txtPrevAmount);
    var amount = parseInt(txtAmount);
    var price = parseFloat(txtPrice);
    var total = parseFloat(document.getElementById("totalAllItem").value) - prevAmount * price;
    if (amount > 0 || txtAmount === "") {
        if (txtAmount === "") {
            amount = 0;
        }
    } else {
        amount = 1;
    }
    document.getElementById(totalID).innerHTML = (amount * price).toFixed(1);
    document.getElementById(UpdateAmountID).value = itemID + ";;;;;" + itemname + ";;;;;" + amount;

    document.getElementById("totalAllItem").value = (total + amount * price).toFixed(1);
    document.getElementById(prevAmountID).value = amount;
    document.getElementById(AmountID).value = amount;
}


function checkValidDate(minID, maxID, errorBox, searchButton) {
    var txtMinDate = document.getElementById(minID).value;
    var txtMaxDate = document.getElementById(maxID).value;

    try {
        var minDate = new Date();
        var maxDate = new Date();

        if (txtMinDate === "") {
            document.getElementById(minID).value = "1753-01-01";
            minDate = "1753-01-01";
        } else {
            minDate = txtMinDate;
        }
        if (txtMaxDate === "") {
            document.getElementById(maxID).value = document.getElementById(maxID).max;
            maxDate = document.getElementById(maxID).max;
        } else {
            maxDate = txtMaxDate;
        }

        if (minDate <= maxDate) {
            document.getElementById(errorBox).style.visibility = "hidden";
            document.getElementById(errorBox).innerHTML = "Invalid date range!";
            document.getElementById(searchButton).disabled = false;
            document.getElementById(searchButton).style.opacity = "1";
        } else {
            document.getElementById(errorBox).style.visibility = "visible";
            document.getElementById(searchButton).disabled = true;
            document.getElementById(searchButton).style.opacity = "0.6";
        }
    } catch (e) {
        document.getElementById(errorBox).innerHTML = "Invalid date format!";
        document.getElementById(errorBox).style.visibility = "visible";
    }
}