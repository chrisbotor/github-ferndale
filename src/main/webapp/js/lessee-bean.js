
    // alert('in lessee-bean.js!');
    

//##############################################		LESSEE BEAN 		####################################### //
    
    var LesseeBean = Ext.data.Record.create(
	[
		{ name: 'id', type: 'int' },
		{ name: 'userId', type: 'int' },
		{ name: 'houseId', type: 'int' },
		{ name: 'lastName', type: 'string' },
		{ name: 'firstName', type: 'string' },
		{ name: 'middleName', type: 'string' },
		{ name: 'birthDate', type: 'string' },
		{ name: 'civilStatus', type: 'string' },
		{ name: 'mobileNumber', type: 'string' },
		{ name: 'homeNumber', type: 'string' },
		{ name: 'emailAddress', type: 'string' },
		{ name: 'workName', type: 'string' },
		{ name: 'workAddress', type: 'string' },
		{ name: 'workLandline', type: 'string' },
		{ name: 'workMobile', type: 'string' },
		{ name: 'workEmail', type: 'string' },
		{ name: 'status', type: 'string' },
		{ name: 'createdAt', type: 'string' },
		{ name: 'updatedAt', type: 'string' },
		{ name: 'lesseeName', type: 'string' }
    ]);

    
    
 // #######################		LESSEE BEAN PROXY		########################## //
        
        var lesseeBeanProxy = new Ext.data.HttpProxy({
            api: {
                read: 'lessee/get-all-lessee.action'
                //update  : 'lessee/update-lessee.action'
            }
        });
        
        
        
    
 // #######################		LESSEE BEAN READER		########################## //
    
    var lesseeBeanReader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'
      },
      	LesseeBean);
  
  
 
       
 
 // #######################		LESSEE BEAN STORE		########################## //
    
    var lesseeBeanStore = new Ext.data.Store({
        id: 'lesseeBeanStore',
        proxy: lesseeBeanProxy,
        reader: lesseeBeanReader,
        //writer: serwriter,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false
    });
    
 
    
    

 // #######################		IMPORTANT! OTHERWISE, THE PROXY WILL NOT BE DETECTED		########################## //
    
    //read the data from simple array
    lesseeBeanStore.load();
 