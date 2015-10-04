Ext.onReady(function(){

    // Ext.BLANK_IMAGE_URL = '../ext-3.4.0/resources/images/default/s.gif';
	
	Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';

    // alert('in create-request page!');

   // searchField.load();
    
    // ownerBeanStore.load();
   // lesseeBeanStore.load();
 
    
 // #######################			FIELD SETS 		########################## //
    
    var searchTypeItem = {
    		fieldLabel: 'SEARCH TYPE',
    		name: 'searchType',
    		id: 'searchType',
    		xtype:'combo',
            store: new Ext.data.ArrayStore({
                fields: ['searchTypeId', 'searchTypeDescription'],
                data : 
                [
                 	['1', 'Owner'],
                 	['2', 'Lessee'],
                 	['3', 'House']
                ]
            }),
            displayField:'searchTypeDescription',
            valueField: 'searchTypeId',
            mode: 'local',
            typeAhead: false,
            triggerAction: 'all',
            lazyRender: true,
            editable:false,
            emptyText: 'Select Type of Search',
            allowBlank: false,
            blankText: 'Search type should not be null',
            anchor: '100%',
            listeners:{
            	select:  function(combo, record, index){
               
           			var searchTypeId = combo.getValue();
           			// alert('SearchTypeId: ' + searchTypeId);
           			
           			if (searchTypeId == 1)
           			{
           				Ext.getCmp('lesseeName').hide();
           				hideLesseeDetails();
           				
           				Ext.getCmp('ownerName').show();
           			}
           			else if (searchTypeId == 2)
           			{
           				// alert('Lessee!');
           				Ext.getCmp('ownerName').hide();
           				hideOwnerDetails();
           				
           				Ext.getCmp('lesseeName').show();
           			}
           		}
            }
    }
    
    
    
    // OWNER NAME
    var searchOwnerItem = {
   		 fieldLabel: 'OWNER NAME',
       	 name: 'ownerName',		// NOTE: name is used by the Java controller to get the field value, id is used by the Extjs function getCmp(id)
       	 id: 'ownerName',
       	 xtype:'combo',
       	 store: ownerBeanStore,
       	 displayField:'ownerName',
         valueField: 'id',
         queryMode: 'server',
         mode: 'local',
         typeAhead: true,
         triggerAction: 'all',
         lazyRender: true,
         // editable:false,
         emptyText: 'Select owner name',
         //allowBlank: false,
         blankText: 'Owner name should not be null',
         hidden:true,
         anchor: '100%',
         listeners:{
                select:  function(combo, record, index)
                {
                	// RACS
                	var ownrId = combo.getValue();
                	var usrId = record.get('userId');
                	
                	// set the owner id and user id
                	Ext.getCmp('ownerId').setValue(ownrId);
                	Ext.getCmp('ownerUserId').setValue(usrId);
                	
                	showOwnerDetails(record);
                }
            }
   }
    
    
    
  //############################################## OWNER HIDDEN FIELDS ######################################################   
    // OWNER ID
    var ownerIdItem = {
    	name: 'ownerId',
        id:'ownerId',
        xtype: 'textfield',
        hidden:true,
        anchor: '100%'
   }
    
    // OWNER USER ID
    var ownerUserIdItem = {
    	name: 'ownerUserId',
        id:'ownerUserId',
        xtype: 'textfield',
        hidden:true,
        anchor: '100%'
   }
    
    // FIRST NAME
    var ownerFirstNameItem = {
    	fieldLabel: 'FIRST NAME',
    	name: 'ownerFirstName',
    	id:'ownerFirstName',
    	xtype: 'textfield',
    	hidden:true,
    	disabled: true,
        anchor: '100%'
    }
    
    // MIDDLE NAME
    var ownerMiddleNameItem = {
    	fieldLabel: 'MIDDLE NAME',
    	name: 'ownerMiddleName',
    	id:'ownerMiddleName',
    	xtype: 'textfield',
    	hidden:true,
    	// disabled: true,
        anchor: '100%'
    }
    
    // LAST NAME
    var ownerLastNameItem = {
    	fieldLabel: 'LAST NAME',
    	name: 'ownerLastName',
    	id:'ownerLastName',
    	xtype: 'textfield',
    	hidden:true,
    	disabled: true,
        anchor: '100%'
    }
    
    // CIVIL STATUS
    var ownerCivilStatusItem = {
    	fieldLabel: 'CIVIL STATUS',
    	name: 'ownerCivilStatus',
    	id:'ownerCivilStatus',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    // ECY
    // MOBILE NUMBER
    var ownerMobileNumberItem = {
    	fieldLabel: 'MOBILE NUMBER',
    	name: 'ownerMobileNumber',
    	id:'ownerMobileNumber',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    // HOME NUMBER
    var ownerHomeNumberItem = {
    	fieldLabel: 'HOME NUMBER',
    	name: 'ownerHomeNumber',
    	id:'ownerHomeNumber',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    // EMAIL ADDRESS
    var ownerEmailAddressItem = {
    	fieldLabel: 'EMAIL ADDRESS',
    	name: 'ownerEmailAddress',
    	id:'ownerEmailAddress',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    // 	WORK NAME (COMPANY NAME)
    var ownerWorkNameItem = {
    	fieldLabel: 'COMPANY NAME',
    	name: 'ownerWorkName',
    	id:'ownerWorkName',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    // 	WORK ADDRESS (COMPANY NAME)
    var ownerWorkAddressItem = {
    	fieldLabel: 'WORK ADDRESS',
    	name: 'ownerWorkAddress',
    	id:'ownerWorkAddress',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    // 	WORK LANDLINE (WORK TELEPHONE NUMBER)
    var ownerWorkLandlineItem = {
    	fieldLabel: 'TELEPHONE NUMBER',
    	name: 'ownerWorkLandline',
    	id:'ownerWorkLandline',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    // 	WORK MOBILE (MOBILE NUMBER)
    var ownerWorkMobileItem = {
    	fieldLabel: 'MOBILE NUMBER',
    	name: 'ownerWorkMobile',
    	id:'ownerWorkMobile',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    // 	WORK EMAIL
    var ownerWorkEmailItem = {
    	fieldLabel: 'WORK EMAIL',
    	name: 'ownerWorkEmail',
    	id:'ownerWorkEmail',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    // 	BIRTH DATE
    var ownerBirthDateItem = {
    	fieldLabel: 'BIRTH DATE',
    	name: 'ownerBirthDate',
    	id:'ownerBirthDate',
    	hidden:true,
        xtype: 'datefield',
        format: 'm/d/Y',
        // submitFormat: 'm/d/Y',
        submitFormat: 'Y/m/d',
        anchor: '100%'
    }
    
    // 	STATUS
    var ownerStatusItem = {
    	fieldLabel: 'STATUS',
    	name: 'ownerStatus',
    	id:'ownerStatus',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    
    
    
    // LESSEE NAME
    var searchLesseeItem = {
   		 fieldLabel: 'LESSEE NAME',
       	 name: 'lesseeName',		// NOTE: name is used by the Java controller to get the field value, id is used by the Extjs function getCmp(id)
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
                select:  function(combo, record, index)
                {
                	// CHRIS
                	var lesseeId = combo.getValue();
                	var lesseeUserId = record.get('userId');
                	var lesseeHouseId = record.get('houseId');
                	
                	// alert('lesseeId: ' + lesseeId);
                	// alert('userId: ' + lesseeUserId);
                	
                	// set the user id and house id
                	Ext.getCmp('lesseeId').setValue(lesseeId);
                	Ext.getCmp('lesseeUserId').setValue(lesseeUserId);
                	Ext.getCmp('lesseeHouseId').setValue(lesseeHouseId);
                	
                	showLesseeDetails(record);
                }
            }
   }
    
    
    
    
//############################################## LESSEE HIDDEN FIELDS ######################################################   
    // LESSEE ID
    var lesseeIdItem = {
    	name: 'lesseeId',
        id:'lesseeId',
        xtype: 'textfield',
        hidden:true,
        anchor: '100%'
   }
    
    // LESSEE USER ID
    var lesseeUserIdItem = {
    	name: 'lesseeUserId',
        id:'lesseeUserId',
        xtype: 'textfield',
        hidden:true,
        anchor: '100%'
   }
    
    var lesseeHouseIdItem = {
        name: 'lesseeHouseId',
        id:'lesseeHouseId',
        xtype: 'textfield',
        hidden:true,
        anchor: '100%'
    }
    
    // FIRST NAME
    var lesseeFirstNameItem = {
    	fieldLabel: 'FIRST NAME',
    	name: 'lesseeFirstName',
    	id:'lesseeFirstName',
    	xtype: 'textfield',
    	hidden:true,
    	disabled: true,
        anchor: '100%'
    }
    
    // MIDDLE NAME
    var lesseeMiddleNameItem = {
    	fieldLabel: 'MIDDLE NAME',
    	name: 'lesseeMiddleName',
    	id:'lesseeMiddleName',
    	xtype: 'textfield',
    	hidden:true,
    	// disabled: true,
        anchor: '100%'
    }
    
    // LAST NAME
    var lesseeLastNameItem = {
    	fieldLabel: 'LAST NAME',
    	name: 'lesseeLastName',
    	id:'lesseeLastName',
    	xtype: 'textfield',
    	hidden:true,
    	disabled: true,
        anchor: '100%'
    }
    
    // CIVIL STATUS
    var lesseeCivilStatusItem = {
    	fieldLabel: 'CIVIL STATUS',
    	name: 'lesseeCivilStatus',
    	id:'lesseeCivilStatus',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    // MOBILE NUMBER
    var lesseeMobileNumberItem = {
    	fieldLabel: 'MOBILE NUMBER',
    	name: 'lesseeMobileNumber',
    	id:'lesseeMobileNumber',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    // HOME NUMBER
    var lesseeHomeNumberItem = {
    	fieldLabel: 'HOME NUMBER',
    	name: 'lesseeHomeNumber',
    	id:'lesseeHomeNumber',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    // EMAIL ADDRESS
    var lesseeEmailAddressItem = {
    	fieldLabel: 'EMAIL ADDRESS',
    	name: 'lesseeEmailAddress',
    	id:'lesseeEmailAddress',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    // 	WORK NAME (COMPANY NAME)
    var lesseeWorkNameItem = {
    	fieldLabel: 'COMPANY NAME',
    	name: 'lesseeWorkName',
    	id:'lesseeWorkName',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    // 	WORK ADDRESS (COMPANY ADDRESS)
    var lesseeWorkAddressItem = {
    	fieldLabel: 'WORK ADDRESS',
    	name: 'lesseeWorkAddress',
    	id:'lesseeWorkAddress',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    // 	WORK LANDLINE (WORK TELEPHONE NUMBER)
    var lesseeWorkLandlineItem = {
    	fieldLabel: 'TELEPHONE NUMBER',
    	name: 'lesseeWorkLandline',
    	id:'lesseeWorkLandline',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    // 	WORK MOBILE (MOBILE NUMBER)
    var lesseeWorkMobileItem = {
    	fieldLabel: 'WORK MOBILE NUMBER',
    	name: 'lesseeWorkMobile',
    	id:'lesseeWorkMobile',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    // 	WORK EMAIL
    var lesseeWorkEmailItem = {
    	fieldLabel: 'WORK EMAIL',
    	name: 'lesseeWorkEmail',
    	id:'lesseeWorkEmail',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    // 	BIRTH DATE
    var lesseeBirthDateItem = {
    	fieldLabel: 'BIRTH DATE',
    	name: 'lesseeBirthDate',
    	id:'lesseeBirthDate',
    	hidden:true,
        xtype: 'datefield',
        format: 'm/d/Y',
        // submitFormat: 'm/d/Y',
        submitFormat: 'Y/m/d',
        anchor: '100%'
    }
    
    // 	STATUS
    var lesseeStatusItem = {
    	fieldLabel: 'STATUS',
    	name: 'lesseeStatus',
    	id:'lesseeStatus',
    	xtype: 'textfield',
    	hidden:true,
        anchor: '100%'
    }
    
    
    
    
// #######################			REQUEST FORM 		########################## //
    var requestForm = {
        columnWidth: .98,
        id: 'requestFormId',
        layout: 'form',
        align:'center',
        items: [searchTypeItem, searchOwnerItem, ownerIdItem, ownerUserIdItem, ownerFirstNameItem, ownerMiddleNameItem, ownerLastNameItem, ownerCivilStatusItem, ownerMobileNumberItem,
                ownerHomeNumberItem, ownerEmailAddressItem, ownerWorkNameItem, ownerWorkAddressItem, ownerWorkLandlineItem, ownerWorkMobileItem, 
                ownerWorkEmailItem, ownerBirthDateItem, ownerStatusItem, searchLesseeItem, lesseeIdItem, lesseeUserIdItem, lesseeHouseIdItem, lesseeFirstNameItem, lesseeMiddleNameItem,
                lesseeLastNameItem, lesseeCivilStatusItem, lesseeMobileNumberItem, lesseeHomeNumberItem, lesseeEmailAddressItem, lesseeWorkNameItem, lesseeWorkAddressItem,
                lesseeWorkLandlineItem, lesseeWorkMobileItem, lesseeWorkEmailItem, lesseeBirthDateItem, lesseeStatusItem] // RACS
	}

   
 // FORM PANEL
    var createRequestFormPanel = new Ext.FormPanel({
        title: 'SEARCH FOR OWNER/HOUSE/LESSEE',
        frame: true,
        autoScroll:true,
        cls: 'my-form-class',
        // bodyStyle:'padding:5px',
        labelWidth : 130,   // width ng label like 'REQUEST TYPE'
        width:400,
        height:500,
        padding: 5,
        labelAlign : 'center',
        items: [{
            items: [{
                align:'center',
                layout: 'column',
                items: [requestForm]
            }]
        }],
        buttons: [{
            id: 'createRequestSubmitBtn',
            text: 'Save',
            handler: function() {
                fnSubmitRequest(createRequestFormPanel);
            }
        },{
        	id: 'createRequestResetBtn',
            text: 'Reset',
            disabled: false,
            handler: function() {
                fnResetForm(createRequestFormPanel);
            }
        }]
    });
    
  
    
    // submit the form
    function fnSubmitRequest(theForm)
    {
    	// alert('Submitting the form...');
    	
    	var submitUrl = "";
    	var searchType = Ext.getCmp('searchType').getValue();
    	// alert('searchType: ' + searchType);
    	
    	if (searchType == 1)
    	{
    		submitUrl = 'owner/update-owner.action';
    	}
    	else if (searchType == 2)
    	{
    		submitUrl = 'lessee/update-lessee.action';
    	}
    	
    	// alert('submitUrl: ' + submitUrl);
    	
    	
    	
    	theForm.getForm().submit({
            url: submitUrl,
            waitMsg: 'loading...',
            success: function(form, action) {
                Ext.Msg.alert('Success', 'Successfully saved owner details.');
                fnResetForm(theForm);
            },
            failure: function(form, action) {
                Ext.Msg.alert('Warning', 'Error in saving owner details. Please check the information entered.');
            }
        });
    }
    
    
    
    // SHOW OWNER DETAILS
    function showOwnerDetails(record)
    {
    	if (record != null)
    	{
    		var firstName = record.get('firstName');
        	var middleName = record.get('middleName');
        	var lastName = record.get('lastName');
        	var civilStatus = record.get('civilStatus');
        	var mobileNumber = record.get('mobileNumber');
        	var homeNumber = record.get('homeNumber');
        	var emailAddress = record.get('emailAddress');
        	var workName = record.get('workName');
        	var workAddress = record.get('workAddress');
        	var workLandline = record.get('workLandline');
        	var workMobile = record.get('workMobile');
        	var workEmail = record.get('workEmail');
        	var birthDate = record.get('birthDate');
        	var status = record.get('status');
        	
        	// ('ownerId: ' + ownerId);
        	// alert('firstName: ' + firstName);
        	
        	
        	Ext.getCmp('ownerFirstName').show();
        	Ext.getCmp('ownerFirstName').setValue(firstName);
        	
        	Ext.getCmp('ownerMiddleName').show();
        	Ext.getCmp('ownerMiddleName').setValue(middleName);
        	
        	Ext.getCmp('ownerLastName').show();
        	Ext.getCmp('ownerLastName').setValue(lastName);
        	
        	Ext.getCmp('ownerCivilStatus').show();
        	Ext.getCmp('ownerCivilStatus').setValue(civilStatus);
        	
        	Ext.getCmp('ownerMobileNumber').show();
        	Ext.getCmp('ownerMobileNumber').setValue(mobileNumber);
        	
        	Ext.getCmp('ownerHomeNumber').show();
        	Ext.getCmp('ownerHomeNumber').setValue(homeNumber);
        	
        	Ext.getCmp('ownerEmailAddress').show();
        	Ext.getCmp('ownerEmailAddress').setValue(emailAddress);
        	
        	Ext.getCmp('ownerWorkName').show();
        	Ext.getCmp('ownerWorkName').setValue(workName);
        	
        	Ext.getCmp('ownerWorkAddress').show();
        	Ext.getCmp('ownerWorkAddress').setValue(workAddress);
        	
        	Ext.getCmp('ownerWorkLandline').show();
        	Ext.getCmp('ownerWorkLandline').setValue(workLandline);
        	
        	Ext.getCmp('ownerWorkMobile').show();
        	Ext.getCmp('ownerWorkMobile').setValue(workMobile);
        	
        	Ext.getCmp('ownerWorkEmail').show();
        	Ext.getCmp('ownerWorkEmail').setValue(workEmail);
        	
        	Ext.getCmp('ownerBirthDate').show();
        	Ext.getCmp('ownerBirthDate').setValue(birthDate);
        	
        	Ext.getCmp('ownerStatus').setValue(status);
        }
    }
    
    
    
    // HIDE OWNER DETAILS
    function hideOwnerDetails()
    {
    	Ext.getCmp('ownerName').hide();
       	Ext.getCmp('ownerName').setValue("");
    	
    	Ext.getCmp('ownerFirstName').hide();
       	Ext.getCmp('ownerFirstName').setValue("");
        	
        Ext.getCmp('ownerMiddleName').hide();
        Ext.getCmp('ownerMiddleName').setValue("");
        	
        Ext.getCmp('ownerLastName').hide();
        Ext.getCmp('ownerLastName').setValue("");
        	
        Ext.getCmp('ownerCivilStatus').hide();
        Ext.getCmp('ownerCivilStatus').setValue("");
        	
        Ext.getCmp('ownerMobileNumber').hide();
        Ext.getCmp('ownerMobileNumber').setValue("");
        	
        Ext.getCmp('ownerHomeNumber').hide();
        Ext.getCmp('ownerHomeNumber').setValue("");
        	
        Ext.getCmp('ownerEmailAddress').hide();
        Ext.getCmp('ownerEmailAddress').setValue("");
        	
        Ext.getCmp('ownerWorkName').hide();
        Ext.getCmp('ownerWorkName').setValue("");
        	
        Ext.getCmp('ownerWorkAddress').hide();
        Ext.getCmp('ownerWorkAddress').setValue("");
        	
        Ext.getCmp('ownerWorkLandline').hide();
        Ext.getCmp('ownerWorkLandline').setValue("");
        	
        Ext.getCmp('ownerWorkMobile').hide();
        Ext.getCmp('ownerWorkMobile').setValue("");
        	
        Ext.getCmp('ownerWorkEmail').hide();
        Ext.getCmp('ownerWorkEmail').setValue("");
        	
        Ext.getCmp('ownerBirthDate').hide();
        Ext.getCmp('ownerBirthDate').setValue("");
        	
        Ext.getCmp('ownerStatus').setValue("");
    }
    
    	
    	
    // SHOW LESSEE DETAILS
    function showLesseeDetails(record)
    {
    	if (record != null)
    	{
    		var firstName = record.get('firstName');
        	var middleName = record.get('middleName');
        	var lastName = record.get('lastName');
        	var civilStatus = record.get('civilStatus');
        	var mobileNumber = record.get('mobileNumber');
        	var homeNumber = record.get('homeNumber');
        	var emailAddress = record.get('emailAddress');
        	var workName = record.get('workName');
        	var workAddress = record.get('workAddress');
        	var workLandline = record.get('workLandline');
        	var workMobile = record.get('workMobile');
        	var workEmail = record.get('workEmail');
        	var birthDate = record.get('birthDate');
        	var status = record.get('status');
        	
        	// ('lesseeId: ' + lesseeId);
        	// alert('birthDate: ' + birthDate);
        	
        	
        	Ext.getCmp('lesseeFirstName').show();
        	Ext.getCmp('lesseeFirstName').setValue(firstName);
        	
        	Ext.getCmp('lesseeMiddleName').show();
        	Ext.getCmp('lesseeMiddleName').setValue(middleName);
        	
        	Ext.getCmp('lesseeLastName').show();
        	Ext.getCmp('lesseeLastName').setValue(lastName);
        	
        	Ext.getCmp('lesseeCivilStatus').show();
        	Ext.getCmp('lesseeCivilStatus').setValue(civilStatus);
        	
        	Ext.getCmp('lesseeMobileNumber').show();
        	Ext.getCmp('lesseeMobileNumber').setValue(mobileNumber);
        	
        	Ext.getCmp('lesseeHomeNumber').show();
        	Ext.getCmp('lesseeHomeNumber').setValue(homeNumber);
        	
        	Ext.getCmp('lesseeEmailAddress').show();
        	Ext.getCmp('lesseeEmailAddress').setValue(emailAddress);
        	
        	Ext.getCmp('lesseeWorkName').show();
        	Ext.getCmp('lesseeWorkName').setValue(workName);
        	
        	Ext.getCmp('lesseeWorkAddress').show();
        	Ext.getCmp('lesseeWorkAddress').setValue(workAddress);
        	
        	Ext.getCmp('lesseeWorkLandline').show();
        	Ext.getCmp('lesseeWorkLandline').setValue(workLandline);
        	
        	Ext.getCmp('lesseeWorkMobile').show();
        	Ext.getCmp('lesseeWorkMobile').setValue(workMobile);
        	
        	Ext.getCmp('lesseeWorkEmail').show();
        	Ext.getCmp('lesseeWorkEmail').setValue(workEmail);
        	
        	Ext.getCmp('lesseeBirthDate').show();
        	Ext.getCmp('lesseeBirthDate').setValue(birthDate);
        	
        	Ext.getCmp('lesseeStatus').setValue(status);
        	
    	}
    }
    
    
    // HIDE LESSEE DETAILS
    function hideLesseeDetails()
    {
    	Ext.getCmp('lesseeName').hide();
       	Ext.getCmp('lesseeName').setValue("");
       	
    	Ext.getCmp('lesseeFirstName').hide();
        Ext.getCmp('lesseeFirstName').setValue("");
        
        Ext.getCmp('lesseeMiddleName').hide();
        Ext.getCmp('lesseeMiddleName').setValue("");
        	
        Ext.getCmp('lesseeLastName').hide();
        Ext.getCmp('lesseeLastName').setValue("");
        	
        Ext.getCmp('lesseeCivilStatus').hide();
        Ext.getCmp('lesseeCivilStatus').setValue("");
        	
        Ext.getCmp('lesseeMobileNumber').hide();
        Ext.getCmp('lesseeMobileNumber').setValue("");
        	
        Ext.getCmp('lesseeHomeNumber').hide();
        Ext.getCmp('lesseeHomeNumber').setValue("");
        
        Ext.getCmp('lesseeEmailAddress').hide();
        Ext.getCmp('lesseeEmailAddress').setValue("");
        	
        Ext.getCmp('lesseeWorkName').hide();
        Ext.getCmp('lesseeWorkName').setValue("");
        	
        Ext.getCmp('lesseeWorkAddress').hide();
        Ext.getCmp('lesseeWorkAddress').setValue("");
        	
        Ext.getCmp('lesseeWorkLandline').hide();
        Ext.getCmp('lesseeWorkLandline').setValue("");
        	
        Ext.getCmp('lesseeWorkMobile').hide();
        Ext.getCmp('lesseeWorkMobile').setValue("");
        	
        Ext.getCmp('lesseeWorkEmail').hide();
        Ext.getCmp('lesseeWorkEmail').setValue("");
        	
        Ext.getCmp('lesseeBirthDate').hide();
        Ext.getCmp('ownerBirthDate').setValue("");
        	
        Ext.getCmp('lesseeStatus').setValue("");
        Ext.getCmp('lesseeHouseId').setValue("");
    }
    
    
    
    // reset form
    function fnResetForm(theForm)
    {
        theForm.getForm().reset();
        Ext.getCmp('createRequestSubmitBtn').setDisabled(false);
        Ext.getCmp('createRequestResetBtn').setDisabled(false);
        
        // enable these for aesthetic reasons
        Ext.getCmp('ownerFirstName').enable();
    	Ext.getCmp('ownerMiddleName').enable();
    	Ext.getCmp('ownerLastName').enable();
    	
    	location.reload();
    }
    
    
 	
    // render the Ext Js element in a div with id="login"
	createRequestFormPanel.render('admin-create-request-table');
    
    
	// populate Search Field combo box based on search type selected
	/*function fnPopulateSearchField(searchTypeId)
	{
		var searchFieldCombo = 	Ext.getCmp('searchField');
		
		searchFieldCombo.clearValue(); 
		
		// retrieve house/owner/lessee based on the searchTypeId
		searchFieldCombo.store.load({
			params: { searchTypeId: searchTypeId}
		})
	}*/
     
    
});