// ###################################################      SET THE ATTRIBUTE OF THE BACK TO HOME     ######################################################################
    
    // this is the house id that came from the home owner landing page
    var params = Ext.urlDecode(location.search.substring(1));
    var houseId = params.houseId;
    var houseNum = params.houseNum;
    var address = params.address;
    var delinquent = params.del;
    
    var decodedAddress = decodeURIComponent(address.replace(/\+/g, '%20') );
    
    var homeLink =  document.getElementById('homeLink').getAttribute('href');
    homeLink = homeLink + '?houseId=' + houseId + '&address=' + address + '&houseNum=' + houseNum + '&del=' + delinquent;
    
    // set the home.action link
    document.getElementById('homeLink').href = homeLink;
    
    // set the Owner's Address
    var ownerAddress = document.getElementById('ownerAddress')
    ownerAddress.innerHTML = decodedAddress;
    ownerAddress.style.color = "green";
    ownerAddress.style.fontWeight = 'bold';
    
