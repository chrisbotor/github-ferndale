
    // alert('in owner-bean.js!');
    

//##############################################		OWNER BEAN 		####################################### //
    
    var OwnerBean = Ext.data.Record.create(
	[
		{ name: 'id', type: 'int' },
		{ name: 'userId', type: 'int' },
		{ name: 'lastName', type: 'string' },
		{ name: 'firstName', type: 'string' },
		{ name: 'middleName', type: 'string' },
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
		{ name: 'birthDate', type: 'string' },
		{ name: 'createdAt', type: 'string' },
		{ name: 'updatedAt', type: 'string' },
		{ name: 'ownerName', type: 'string' }
    ]);

    
    
 // #######################		OWNER BEAN PROXY		########################## //
        
        var ownerBeanProxy = new Ext.data.HttpProxy({
            api: {
                read: 'owner/get-all-owner.action'
                //update  : 'owner/update-owner.action'
            }
        });
        
        
        
    
 // #######################		OWNER BEAN READER		########################## //
    
    var ownerBeanReader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'
      },
      	OwnerBean);
  
  
 
       
 
 // #######################		OWNER BEAN STORE		########################## //
    
    var ownerBeanStore = new Ext.data.Store({
        id: 'ownerBeanStore',
        proxy: ownerBeanProxy,
        reader: ownerBeanReader,
        //writer: serwriter,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false
    });
    
 
    
    

 // #######################		IMPORTANT! OTHERWISE, THE PROXY WILL NOT BE DETECTED		########################## //
    
    //read the data from simple array
    ownerBeanStore.load();
 