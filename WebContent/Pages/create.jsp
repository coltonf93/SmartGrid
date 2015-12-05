<form action="index.jsp" class="form-wrapper" method="POST">
	<label class="text"><span>Test Name</span><div class="input-wrapper"><input type="text" name="testName"/></div></label>
	<label class="text"><span>My Name</span><div class="input-wrapper"><input type="text" name="testName"/></div></label>
	<input type="submit" value="Submit" />
</form>
<div class="form-wrapper">
<fieldset class="radio-check-label" id="color">
  <span class="label">Choose your theme</span>
  <div class="input-wrapper">
    <label class="radio" id="dark">
      <input type="radio" name="color" value="dark"/>
      <span>Dark version</span>
    </label>
  </div>
  <div class="input-wrapper">
    <label class="radio" id="light">
      <input type="radio" name="color" value="light"/>
      <span>Light version</span>
    </label>
  </div>
  <div class="input-wrapper">
    <label class="radio" id="none">
      <input type="radio" name="color" value="none"/>
      <span>Oh boy, your design suxx. Giv' me a clean version.</span>
    </label>
  </div>
</fieldset>
</div>

<div id="form" class="form-wrapper">
  <label class="text">
    <span>Text and Values</span>
    <div class="input-wrapper">
      <input type="text" /> 
    </div>
  </label>

  <label class="dropdown">
    <span>Dropdown</span>
    <div class="input-wrapper">
      <select size="1">
        <option>-- Please choose --</option>
        <option value="1">Option 1</option>
        <option value="2">Option 2</option>
        <option value="3">Option 3</option>    
      </select>
    </div>
  </label>

  <label class="multiple">  
    <span>Multiple</span>
    <div class="input-wrapper">
      <select class="multiple" size="3">
        <option value="1">Option 1</option>
        <option value="2">Option 2</option>
        <option value="3">Option 3</option>    
      </select>  
    </div>
  </label>
  
  <label class="text">
    <span>Some more information and also a label with a lot of text. So resize your browser and see whats happening...</span>
    <div class="input-wrapper">
      <textarea>Write, some text here  </textarea> 
    </div>
  </label>
  
  <fieldset class="radio-check-label">
    <span class="label">Do you like this?</span>
    
    <div class="input-wrapper">
      <label class="radio" for="yes">
        <input type="radio" name="foo" value="yes" id="yes"/>
        <span>Yes, please</span>
      </label>
    </div>
    
    <div class="input-wrapper">
      <label class="radio" for="no" >
        <input type="radio" name="foo" value="no" id="no"/>
        <span>No, thanks</span>
      </label>
    </div>
    
    <div class="input-wrapper">    
      <label class="radio" for="maybe" for="maybe">
        <input type="radio" name="foo" value="maybe" id="maybe"/>
        <span>Well, maybe</span>
      </label>
    </div>
  </fieldset>
  
  <fieldset class="radio-check-label">
    <span class="label">Please check all</span>
    <div class="input-wrapper">
      <label class="checkbox" for="accept">
        <input type="checkbox" name="accept" id="accept"/>
        <span>Okay, I accept all u want</span>
      </label>
    </div>
    
    <div class="input-wrapper">
      <label class="checkbox" for="spam" >
        <input type="checkbox" name="spam" id="spam"/>
        <span>Yes, send me all your spam.</span>
      </label>
    </div>
    
    <div class="input-wrapper">  
      <label class="checkbox" for="toolbars" >
        <input type="checkbox" name="toolbars" id="toolbars"/>
        <span>Install 1000 toolbars and add all available plugins to my browser</span>
      </label>

    </div>
  </fieldset>
  

    <input type="submit" name="submit" value="Submit"/>
    <input type="reset" name="reset" value="Reset"/>

<div class="clear"></div>
<span class="notes">* Is mandetory.</span>
</div>  

