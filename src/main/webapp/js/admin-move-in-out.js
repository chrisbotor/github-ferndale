
Ext.onReady(function(){



    Ext.QuickTips.init();



    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';


    Ext.data.DataProxy.addListener('exception', function(proxy, type, action, options, res) {

        Ext.Msg.show({

            title: 'ERROR',

            msg: res.message,

            icon: Ext.MessageBox.ERROR,

            buttons: Ext.Msg.OK

        });

    });



    // mystore.load();

    

    var existingOwner = {
    	columnWidth: .98,
    	layout: 'form',
    	align:'center',
    	items: [
    	{
    		xtype: 'fieldset',
    		autoHeight: true,
    		//defaultType: 'textfield', 
    		items: [
    		{
    			fieldLabel: 'ACTION',
    			name: 'action',
    			id:'action',
    			cls: 'cTextAlign',
    			xtype:'combo',
    			queryMode: 'server',
    			store: new Ext.data.ArrayStore({
    					fields: ['abbr', 'choice'],
    					data : [
    					        ['mi', 'Move in'],
    					        ['mo', 'Move out']
    					]
    			}),
    			displayField:'choice',
    			valueField: 'abbr',
    			mode: 'local',
    			typeAhead: false,
    			triggerAction: 'all',
    			lazyRender: true,
    			editable:false,
    			emptyText: 'Select action',
    			allowBlank: false,
    			blankText: 'Rented should not be null',
    			anchor: '100%',
    			listeners:{
    				select: function(combo, record, index)
    				{
    					var value = combo.getValue();
    					// alert('value: ' + value);

    					if (value == 'mi')
    					{
    						showMoveInDetails();
    						hideMoveOutDetails();
    						hideLesseeDetails();
    					}
    					else if (value == 'mo')
    					{
    						hideMoveInDetails();
    						showMoveOutDetails();

    						/* Ext.getCmp('idfirstname').hide();
							Ext.getCmp('idmiddlename').hide();
							Ext.getCmp('idlastname').hide();
							Ext.getCmp('ownerId').show();*/
    					}
    				}
    			}
    		},{
    			fieldLabel: 'LESSEE?',
    			name: 'lessee',
    			id:'lessee',
    			cls: 'cTextAlign',
    			xtype:'combo',
    			hidden: true,
    			queryMode: 'server',
    			store: new Ext.data.ArrayStore({
    				fields: ['abbr', 'action'],
    				data : [
    				        ['Y', 'Y'],
    				        ['N', 'N']
    				]
    			}),

                displayField:'action',
                valueField: 'abbr',
                mode: 'local',
                typeAhead: false,
                triggerAction: 'all',
                lazyRender: true,
                editable:false,
                emptyText: 'Select action',
                allowBlank: true,
                blankText: 'Lessee should not be null',
                anchor: '100%',
                listeners:{
                	select: function(combo, record, index) {
                		if(combo.getValue() == 'Y'){

                			Ext.getCmp('ownerId').hide();
                			Ext.getCmp('lesseeName').show();
                			Ext.getCmp('address').show();
                			Ext.getCmp('moveInOutDate').show();
                			
                			Ext.getCmp('address').setValue("");
                			Ext.getCmp('moveInOutDate').setValue("");
                			
                			
                			/*Ext.getCmp('idfirstname').show();

                                Ext.getCmp('idmiddlename').show();

                                Ext.getCmp('idlastname').show();

                                Ext.getCmp('ownerId').hide();*/
                		}
                		else
                		{
                			// RACS
                			Ext.getCmp('lesseeName').hide();
                			Ext.getCmp('lesseeName').setValue("");
                			
                			Ext.getCmp('ownerId').show();
                			Ext.getCmp('address').show();
                			Ext.getCmp('moveInOutDate').show();
                			
                			Ext.getCmp('address').setValue("");
                			Ext.getCmp('moveInOutDate').setValue("");
                			
                			/*Ext.getCmp('idfirstname').hide();

                                Ext.getCmp('idmiddlename').hide();

                                Ext.getCmp('idlastname').hide();

                                Ext.getCmp('ownerId').show();*/
                		}
                	}
                }
    		},{
    			fieldLabel: 'LESSEE NAME',
    			name: 'lesseeName',	// NOTE: name is used by the Java controller to get the field value, id is used by the Extjs function getCmp(id)
    			id: 'lesseeName',
    			xtype:'combo',
    			store: lesseeBeanStore,
    			displayField:'lesseeName',
    			valueField: 'id',
    			queryMode: 'server',
    			mode: 'local',
    			typeAhead: true,
    			triggerAction: 'all',
    			lazyRender: true,
    			// editable:false,
    			emptyText: 'Select lessee name',
    			//allowBlank: false,
    			blankText: 'Lessee name should not be null',
    			hidden:true,
    			anchor: '100%',
    			listeners:{
    				select:  function(combo, record, index){

                       // CHRIS
    					var lesseeId = combo.getValue();
    					var lesseeUserId = record.get('userId');
    					var lesseeHouseId = record.get('houseId');
    					
    					Ext.getCmp('userId').setValue(lesseeUserId);
    					
    					

    					// alert('lesseeId: ' + lesseeId);

                       // alert('lesseeUserId: ' + lesseeUserId);

                       // alert('lesseeHouseId: ' + lesseeHouseId);

                       

    					// set the user id and house id
    					/*Ext.getCmp('lesseeId').setValue(lesseeId);

                        Ext.getCmp('lesseeUserId').setValue(lesseeUserId);

                        Ext.getCmp('lesseeHouseId').setValue(lesseeHouseId);
                       Ext.getCmp('houseIdItem').setValue(lesseeHouseId);
                       alert('HOUSE ID: ' + Ext.getCmp('houseIdItem').getValue())

                       

                       

                       

                       showLesseeDetails(record);*/

                       

                       fnPopulateLesseeAddress(lesseeHouseId);

                       Ext.getCmp('moveInOutDate').show(); // ECY

                       

                       }

                   }

            },// CHRIS
    		{
    			fieldLabel: 'OWNER',
    			name: 'owner',
    			id:'ownerId',
    			xtype:'combo',
    			cls: 'cTextAlign',
    			queryMode: 'server',
    			store: ownerBeanStore,
    			displayField:'ownerName',
    			hiddenName: 'id',
    			valueField: 'id',
    			mode: 'local',
    			// typeAhead: false,
    			triggerAction: 'all',
    			lazyRender: true,
    			// editable:false,
    			emptyText: 'Select action',
    			defaultValue:'0',
    			anchor: '100%',
    			listeners:{
    				select:  function(combo, record, index){
    					var owner = combo.getValue();
    					// alert('Owner ID: ' + owner);

    					Ext.getCmp('ownerIdItem').setValue(owner); // temporary hack
    					fnPopulateAddress(owner);

    					// alert('JobOrderTypeId: ' + jobOrderTypeId);
    					//var existingOwner = combo.getValue();
    					// alert('existingOwner: ' + existingOwner);
    					//	Ext.getCmp('existingOwnerId').setValue(existingOwner);
    				}
    			}
    		},{

            name: 'address',

                id:'address',

            xtype:'combo',

            cls: 'cTextAlign', 

            queryMode: 'server',

            store: houseBeanStore,

            fieldLabel: 'ADDRESS',

            displayField:'address',

            hiddenName: 'id',

            valueField: 'houseId',

            mode: 'local',

            // typeAhead: false,

            triggerAction: 'all',

            lazyRender: true,

            // editable:false,

            emptyText: 'Select action',

            allowBlank: true,

            anchor: '100%',

            listeners:{

                select: function(combo, record, index) 

                {

                var house = combo.value;

               

               // alert('selected house id: ' + house);

                Ext.getCmp('houseIdItem').setValue(house); // temporary hack

               

                      /* var owner = record.get('owner')

                        var rented = record.get('rented')

                        var userId = record.get('userId')

                        Ext.getCmp('ownerId').setValue(owner)

                        Ext.getCmp('rentedId').setValue(rented)

                        Ext.getCmp('userIds').setValue(userId)*/

                }

            }

        },{

                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'USERID',
                name: 'userId',
                id:'userId',
                anchor: '100%',
                hidden:true

            },{

                fieldLabel: 'RENTED',
                name: 'rented',
                id:'rentedId',
                cls: 'cTextAlign',
                xtype:'combo',
                queryMode: 'server',
                store: new Ext.data.ArrayStore({

                    fields: ['abbr', 'action'],
                    data : [
                            ['Y', 'Y'],
                            ['N', 'N']
                    ]

                }),

                displayField:'action',

                valueField: 'abbr',

                mode: 'local',

                typeAhead: false,

                triggerAction: 'all',

                lazyRender: true,

                editable:false,

                emptyText: 'Select action',

                allowBlank: true,

                blankText: 'Rented should not be null',

                anchor: '100%',

                listeners:{

                    select: function(combo, record, index) {

                            if(combo.getValue() == 'Y'){

                                Ext.getCmp('idfirstname').show();

                                Ext.getCmp('idmiddlename').show();

                                Ext.getCmp('idlastname').show();

                                Ext.getCmp('ownerId').hide();

                            }else{

                                Ext.getCmp('idfirstname').hide();

                                Ext.getCmp('idmiddlename').hide();

                                Ext.getCmp('idlastname').hide();

                                Ext.getCmp('ownerId').show();

                            }

                        }

                }

            },{

            cls: 'cTextAlign',

            fieldLabel: 'DATE',

            name: 'moveIn',

            id: 'moveInOutDate',

            xtype: 'datefield',

            format: 'm/d/Y',

            submitFormat: 'm/d/Y',

            editable:false,

            anchor: '100%'

        },{

                cls: 'cTextAlign',

                xtype: 'textfield',

                fieldLabel: 'FIRST NAME',

                name: 'firstname',

                id:'idfirstname',

                anchor: '100%',

                hidden:true

            },{

                cls: 'cTextAlign',

                xtype: 'textfield',

                fieldLabel: 'MIDDLE NAME',

                name: 'middlename',

                id:'idmiddlename',

                anchor: '100%',

                hidden:true

            },{

                xtype: 'textfield',

                fieldLabel: 'LAST NAME',

                name: 'lastname',

                id:'idlastname',

                cls: 'cTextAlign',

                anchor: '100%',

                hidden:true

            }

            ]

        }]

    }

    

    var newLesse = {

        columnWidth: .5,

        layout: 'form',

        id:'newOwner',

        items: [

        {

            xtype: 'fieldset',

            title: 'Owner',

            autoHeight: true,

            defaultType: 'textfield',

            items: [

              

            {

                cls: 'cTextAlign',

                xtype: 'textfield',

                fieldLabel: 'FIRST NAME',

                name: 'firstname',

                id:'idfirstname',

                anchor: '100%'

            },{

                cls: 'cTextAlign',

                xtype: 'textfield',

                fieldLabel: 'MIDDLE NAME',

                name: 'middlename',

                id:'idmiddlename',

                anchor: '100%'

            },{

                xtype: 'textfield',

                fieldLabel: 'LAST NAME',

                name: 'lastname',

                id:'idlastname',

                cls: 'cTextAlign',

                anchor: '100%'

            },

            {

                fieldLabel: 'LESSEE',

                name: 'lessee',

                id:'lessee',

                cls: 'cTextAlign',

                xtype:'combo',

                queryMode: 'server',

                store: new Ext.data.ArrayStore({

                    fields: ['abbr', 'action'],

                    data : [

                    ['Y', 'Y'],

                    ['N', 'N']

                    ]

                }),

                displayField:'action',

                valueField: 'abbr',

                mode: 'local',

                typeAhead: false,

                triggerAction: 'all',

                lazyRender: true,

                editable:false,

                emptyText: 'Select action',

                allowBlank: false,

                blankText: 'Rented should not be null',

                anchor: '100%',

                listeners:{

                    select: function(combo, record, index) {

                            if(combo.getValue() == 'Y'){

                                Ext.getCmp('idfirstname').show();

                                Ext.getCmp('idmiddlename').show();

                                Ext.getCmp('idlastname').show();

                                Ext.getCmp('ownerId').hide();

                            }else{

                                Ext.getCmp('idfirstname').hide();

                                Ext.getCmp('idmiddlename').hide();

                                Ext.getCmp('idlastname').hide();

                                Ext.getCmp('ownerId').show();

                            }

                        }

                }

            }// CHRIS

            ]

        }]

    }



    //var tpl = new Ext.XTemplate('<tpl for="."><div class="x-combo-list-item" >{firstname} {middlename} {lastname}</div></tpl>');

    //Ext.getCmp('myLabel').setVisible(false);

    

// ################################################  HIDDEN FIELDS (TEMPORARY HACK BECAUSE NULL YUNG OWNER ID AT HOUSE ID PAGDATING SA CONTROLLER)  #####################

    // OWNER ID
    var ownerIdItem = {
    	name: 'ownerIdItem',
    	id:'ownerIdItem',
    	xtype: 'textfield',
    	hidden:true,
    	anchor: '100%'
    }


    // HOUSE ID
    var houseIdItem = {
    	name: 'houseIdItem',
    	id:'houseIdItem',
    	xtype: 'textfield',
    	hidden:true,
    	anchor: '100%'
    }

    
    
    

// ##################################################################    FORM    ########################################
    var mf = new Ext.FormPanel({
    	title: 'MOVE IN/ MOVE OUT FORM',
    	frame: true,
    	autoScroll:true,
    	cls: 'my-form-class',
    	bodyStyle:'padding:5px',
    	labelWidth : 100,
    	labelAlign : 'center',
    	width : 450,
    	height : 300,
    	renderTo: 'my-tabs',
    	items: [{
        	align:'left',
        	layout: 'column',
        	items: [existingOwner, ownerIdItem, houseIdItem]
        }],
        buttons: [{
        	id: 'mf.btn.add',
        	text: 'Submit',
        	handler: function(){
        	
        		submitForm(mf.getForm()); // temporary hack
        	}
        },{
        	text: 'Reset',
        	disabled: false,
        	handler: function(){

                fnResetForm(mf.getForm()); // temporary hack
        	}
        }]
    });

    



    var myTabPanel = new Ext.TabPanel({

        //title:"MY TAB",

        //width : 800,

        //height : 350,

        //align:'center',

        //renderTo: 'my-tabs',

        //activeItem: 0,

        //items: [mf]

        });



});



// SUBMIT THE FORM
function submitForm(theForm)
{
	var action = Ext.getCmp('action').getValue();
	// alert('action: ' + action);
    
    var url = "";

    if (action == 'mi'){	// Move In
    	url = 'admin-move-in.action';
    }
    else if (action == 'mo'){	// Move Out
    	url = 'admin-move-out.action';
    }

    // alert('MY URL: ' + url);
    
    theForm.submit({
    	url: url,
    	waitMsg: 'Submitting information...',
    	success: function(form, action){
    		Ext.Msg.alert('Success', 'Information saved.');
    		fnResetForm(theForm);
    	},
    	failure: function(form, action){
    		Ext.Msg.alert('Warning', 'Error in Saving Data');
    	}
    });
}




// RESET THE FORM
function fnResetForm(theForm)
{
	theForm.reset();
	// Ext.getCmp('mf.btn.add').setDisabled(false);
	// Ext.getCmp('mf.btn.reset').setDisabled(false);
} //end fnResetForm





//populate Address combo box based on the Owner id selected
function fnPopulateAddress(ownerId)
{
	var addressCombo = Ext.getCmp('address');
	addressCombo.clearValue(); 

	// retrieve house list based on the owner id
	addressCombo.store.load({
		
		params: { ownerId: ownerId}
	})
}





// RACS
function showMoveInDetails()
{
	Ext.getCmp('ownerId').show();
	Ext.getCmp('address').show();
	Ext.getCmp('rentedId').show();
	Ext.getCmp('moveInOutDate').show();

	// owner
	// address
	// rented
	// date
	// first name 
	// middle name
	// last name to hide
}





function hideMoveInDetails()
{
	// hide values
	Ext.getCmp('ownerId').hide();
	Ext.getCmp('address').hide();  // ADDRESS IS REQUIRED!
	Ext.getCmp('rentedId').hide();  // RENTED IS REQUIRED!
	Ext.getCmp('moveInOutDate').hide();
	Ext.getCmp('idfirstname').hide();
	Ext.getCmp('idmiddlename').hide();
	Ext.getCmp('idlastname').hide();
	

	// clear values
	Ext.getCmp('ownerId').setValue("");
	Ext.getCmp('address').setValue("");
	Ext.getCmp('rentedId').setValue("");
	Ext.getCmp('moveInOutDate').setValue("");
	Ext.getCmp('idfirstname').setValue("");
	Ext.getCmp('idmiddlename').setValue("");
	Ext.getCmp('idlastname').setValue("");
	
	
}


// SHOW MOVE OUT DETAILS
function showMoveOutDetails()
{
	Ext.getCmp('lessee').show();
}


// HIDE MOVE OUT DETAILS
function hideMoveOutDetails()
{
	// hide values
	Ext.getCmp('lessee').hide();
	
	// clear values
	Ext.getCmp('ownerId').setValue("");
	Ext.getCmp('lessee').setValue("");
	Ext.getCmp('address').setValue("");
	Ext.getCmp('rentedId').setValue("");
	Ext.getCmp('moveInOutDate').setValue("");
}


// HIDE LESSEE DETAILS
function hideLesseeDetails()
{
	// hide values
	Ext.getCmp('lesseeName').hide();
	Ext.getCmp('idfirstname').hide();
	Ext.getCmp('idmiddlename').hide();
	Ext.getCmp('idlastname').hide();

	// clear values
	Ext.getCmp('lesseeName').setValue("");
	Ext.getCmp('idfirstname').setValue("");
	Ext.getCmp('lessee').setValue("");
	Ext.getCmp('idmiddlename').setValue("");
	Ext.getCmp('idlastname').setValue("");
}



//POPULATE LESSEE ADDRESS
function fnPopulateLesseeAddress(lesseeHouseId)
{
	var addressCombo = Ext.getCmp('address');
	addressCombo.clearValue(); 
	
	// retrieve house list based on the owner id
	addressCombo.store.load({
		params: { lesseeId: lesseeHouseId}
	})

	addressCombo.show();
}

