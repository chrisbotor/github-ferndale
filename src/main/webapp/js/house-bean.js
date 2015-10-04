
    // alert('in house-bean.js!');
    

//##############################################		HOUSE BEAN 		####################################### //
    
    var HouseBean = Ext.data.Record.create(
	[
		{name: 'houseId', type: 'int'},
		{name: 'ownerId', type: 'int'},
		{name: 'userId', type: 'int'},
		{name: 'address', type: 'string'},
		{name: 'addressNumber', type: 'string'},
		{name: 'addressSt', type: 'string'},
		{name: 'type', type: 'string'},
		{name: 'phase', type: 'int'},
		{name: 'rented', type: 'string'},
		{name: 'title', type: 'string'},
		{name: 'lotArea', type: 'double'}
		
	]);

    
    
 // #######################		HOUSE BEAN PROXY		########################## //
        
        var houseBeanProxy = new Ext.data.HttpProxy({
            api: {
                read: 'house/get-all-house.action'
                //update  : 'owner/update-owner.action'
            }
        });
        
        
        
    
 // #######################		HOUSE BEAN READER		########################## //
    
    var houseBeanReader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'
      },
      	HouseBean);
  
  
 
       
 
 // #######################		HOUSE BEAN STORE		########################## //
    
    var houseBeanStore = new Ext.data.Store({
        id: 'houseBeanStore',
        proxy: houseBeanProxy,
        reader: houseBeanReader,
        //writer: serwriter,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false
    });
    
 
    
    

 // #######################		IMPORTANT! OTHERWISE, THE PROXY WILL NOT BE DETECTED		########################## //
    
    //read the data from simple array
    houseBeanStore.load();
 