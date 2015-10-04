
    // alert('in owner-house page!');
    
    

//##############################################		OWNER - HOUSE  BEAN 		####################################### //
    
    var OwnerHouseBean = Ext.data.Record.create(
			[
			 	{ name: 'userId', type: 'int' },
			 	{ name: 'houseId', type: 'int' },
			 	{ name: 'firstname', type: 'string' },
			 	{ name: 'lastname', type: 'string' },
			 	{ name: 'houseAddrNumber', type: 'string' },
			 	{ name: 'houseAddrStreet', type: 'string' },
			 	{ name: 'ownerHouseLabel', type: 'string' }
			]);

    
    
 // #######################		OWNER - HOUSE DEFINITION PROXY		########################## //
        
        var ownerHouseProxy = new Ext.data.HttpProxy({
            api: {
                read: 'admin/get-owner-house.action'
            }
        });
        
        
        
    
 // #######################		OWNER - HOUSE DEFINITION READER		########################## //
    
    var ownerHouseReader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'
      },
        OwnerHouseBean);
  
  
 
       
 
 // #######################		OWNER - HOUSE DEFINITION STORE		########################## //
    
    var ownerHouseStore = new Ext.data.Store({
        id: 'ownerHouseStore',
        proxy: ownerHouseProxy,
        reader: ownerHouseReader,
        //writer: serwriter,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false
    });
    
 
    
    

 // #######################		IMPORTANT! OTHERWISE, THE PROXY WILL NOT BE DETECTED		########################## //
    
    //read the data from simple array
    ownerHouseStore.load();
 