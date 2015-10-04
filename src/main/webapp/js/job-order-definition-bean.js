
    // alert('in job order definition page!');
    
    

//##############################################		JOB ORDER DEFINITION BEAN 		####################################### //
    
    var JobOrderDefinition = Ext.data.Record.create(
			[
			 	{ name: 'id', type: 'int' },
			 	{ name: 'jobOrderTypeId', type: 'int' },
			 	{ name: 'code', type: 'string' },
			 	{ name: 'description', type: 'string' },
			 	{ name: 'regValue', type: 'float' },
			 	{ name: 'uom', type: 'string' },
			 	{ name: 'maxRegular', type: 'int' },
			 	{ name: 'excessValue', type: 'float' },
			 	{ name: 'createdAt', type: 'string' },
			 	{ name: 'updatedAt', type: 'string' },
            ]);

    
    
 // #######################		JOB ORDER DEFINITION PROXY		########################## //
        
        var jobOrderDefinitionProxy = new Ext.data.HttpProxy({
            api: {
                read: 'common/get-job-order-definition.action'
            }
        });
        
        
        
    
 // #######################		JOB ORDER DEFINITION READER		########################## //
    
    var jobOrderDefinitionReader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'
      },
      	JobOrderDefinition);
  
  
 
       
 
 // #######################		JOB ORDER DEFINITION STORE		########################## //
    
    var jobOrderDefinitionStore = new Ext.data.Store({
        id: 'jobOrderDefinitionStore',
        proxy: jobOrderDefinitionProxy,
        reader: jobOrderDefinitionReader,
        //writer: serwriter,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false
    });
    
 
    
    

 // #######################		IMPORTANT! OTHERWISE, THE PROXY WILL NOT BE DETECTED		########################## //
    
    //read the data from simple array
    jobOrderDefinitionStore.load();
 