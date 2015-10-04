
    // alert('in user-bean.js!');
    

//##############################################		USER BEAN 		####################################### //
    
    var UserBean = Ext.data.Record.create(
	[
		{ name: 'id', type: 'int' },
		{ name: 'username', type: 'string' },
		{ name: 'password', type: 'string' },
		{ name: 'role_id', type: 'int' },
		{ name: 'status', type: 'string' },
		{ name: 'multiOwner', type: 'string' },
		{ name: 'createdAt', type: 'string' },
		{ name: 'updatedAt', type: 'string' },
		{ name: 'fullName', type: 'string' }
	]);

    
    
 // #######################		USER BEAN PROXY		########################## //
        
        var userBeanProxy = new Ext.data.HttpProxy({
            api: {
                read: 'user/get-all-user.action'
                //update  : 'user/update-user.action'
            }
        });
        
        
        
    
 // #######################		USER BEAN READER		########################## //
    
    var userBeanReader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'
      },
      	UserBean);
  
  
 
       
 
 // #######################		USER BEAN STORE		########################## //
    
    var userBeanStore = new Ext.data.Store({
        id: 'userBeanStore',
        proxy: userBeanProxy,
        reader: userBeanReader,
        autoSave: false
    });
    
 
    
    

 // #######################		IMPORTANT! OTHERWISE, THE PROXY WILL NOT BE DETECTED		########################## //
    
    //read the data from simple array
    userBeanStore.load();
 