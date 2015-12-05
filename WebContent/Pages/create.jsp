<section class="content-header">
	<h1>
		Create a Test <small>Fill out the form below</small>
	</h1>
</section>

<section class="content">
	<form action="index.jsp" method="POST">
		<div class="row">
		<div class="col-md-6">
			<div class="box box-warning">
					<div class="info-box">
						<span class="info-box-icon bg-yellow"><i
							class="ion ion-gear-b"></i></span>
						<div class="info-box-content">
							<span class="info-box-number"><h3>General Configuration</h3></span>
						</div>
					</div>
					<div class="box-body">
						<label for="testName">Test Name</label> 
						<input class="form-control input-lg" type="text" name="testName" placeholder="Test Name"> 
						<br>
					    <label for="days">Days</label>
						<input class="form-control" type="number" name="days" min="0" max="9999" placeholder="50">
						<br>
						<label for="connectivity">Connectivity</label>
						<input class="form-control" type="number" name="connectivity" min="0" max="1" value=".3">
						<br> 
						<label for="description">Description</label>
						<input class="form-control input-sm" name="description" type="text" placeholder="Describe the test..." maxlength="100">
					</div>
				</div>
				<div class="box box-info">
					<div class="info-box">
						<span class="info-box-icon bg-aqua"><i
							class="ion ion-ios-world"></i></span>
						<div class="info-box-content">
							<span class="info-box-number"><h3>Main Grid</h3></span>
						</div>
					</div>
					<div class="box-body">
						<label for="mBuy">Static Buy Price</label> 
						<input class="form-control input-lg" name="mBuy" type="number" value=".01" min=".001" max="100"> 
						<br>
					    <label for="mSell">Static Sell Price</label> 
						<input class="form-control input-lg" name="mSell" type="number" value="1" min=".001" max="100"> 
					</div>
				</div>
				<div class="box box-info">
					<div class="info-box">
						<span class="info-box-icon bg-aqua"><i
							class="ion ion-battery-charging"></i></span>
						<div class="info-box-content">
							<span class="info-box-number"><h3>Grid Storage</h3></span>
						</div>
					</div>
					<div class="box-body">
						<label for="stCount">Number of Storage Units</label> 
						<input class="form-control input-lg" type="number" name="stCount" min="0" max="100" value="10"> 
						<br>
						<label for="stCap">Number of Storage Units</label> 
						<input class="form-control input-lg" type="number" name="stCap" min="0" max="500" value="30"> 
						<br>
						<label for="stCapVar">Capacity Variability(+/-)</label> 
						<input class="form-control input-lg" type="number" name="stCapVar" min="0" max="10" value="2"> 
					</div>
				</div>
		</div>
			<div class="col-md-6">
				<div class="box box-danger">
					<div class="info-box">
						<span class="info-box-icon bg-red"><i
							class="ion ion-home"></i></span>
						<div class="info-box-content">
							<span class="info-box-number"><h3>Consumers</h3></span>
						</div>
					</div>
					<div class="box-body">
						<label for="cCount">Number of Consumers</label> 
						<input class="form-control input-lg" type="number" name="cCount" min="0" max="100" value="10"> 
						<br>
					    <label for="cGen">Consumption/Time[1:24]</label>
						<input class="form-control" type="text" name="cGen" maxlength="47" value="1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1">
						<br> 
						<label for="cVar">Consumption Variability(+/-)</label>
						<input class="form-control input-sm" name="cVar" type="number" value="0" min="0" max="10">
					</div>
				</div>
				
				<div class="box box-success">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="ion ion-android-sunny"></i></span>
						<div class="info-box-content">
							<span class="info-box-number"><h3>Solar Generators</h3></span>
						</div>
					</div>
					<div class="box-body">
						<label for="sCount">Number of Solar Generators</label> 
						<input class="form-control input-lg" type="number" name="sCount" min="0" max="100" value="10"> 
						<br>
					    <label for="sGen">Generation/Time[1:24]</label>
						<input class="form-control" type="text" name="sGen" maxlength="47" value="1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1">
						<br> 
						<label for="sVar">Generation Variability(+/-)</label>
						<input class="form-control input-sm" name="sVar" type="number" value="0" min="0" max="10">
					</div>
				</div>
				
				
				<div class="box box-success">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="ion ion-cloud"></i></span>
						<div class="info-box-content">
							<span class="info-box-number"><h3>Wind Generatiors</h3></span>
						</div>
					</div>
					<div class="box-body">
						<label for="wCount">Number of Wind Generators</label> 
						<input class="form-control input-lg" type="number" name="wCount" min="0" max="100" value="10"> 
						<br>
					    <label for="wGen">Generation/Time[1:24]</label>
						<input class="form-control" type="text" name="wGen" maxlength="47" value="1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1">
						<br> 
						<label for="wVar">Wind Variability(+/-)</label>
						<input class="form-control input-sm" name="wVar" type="number" value="0" min="0" max="10">
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-offset-4 col-md-4">
				<div class="box box-primary">
						<div class="box-body" style="align:center;">
							<input type="submit" class="btn btn-block btn-primary btn-lg" value="Run Test" />
						</div>
				</div>
			</div>
		</div>
	</form>
</section>
