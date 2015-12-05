<section class="content-header">
	<h1>
		Create a Test <small>Fill out the form below</small>
	</h1>
</section>

<section class="content">
	<form action="index.jsp" method="POST">
		<div class="row">
			<div class="col-md-6">
			<div class="box box-success">
                <div class="box-header with-border">
                  <h3 class="box-title">Wind Generator Options</h3>
                </div>
                <div class="box-body">
                <label for="wCount">Number of Wind Generators</label>
                  <input class="form-control input-lg" type="number" name="wCount" value="10">
                  <br>
                  <label for="wGenerator">Generation[1:24]</label>
                  <input class="form-control" type="text" name="wGenerate" value="1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1">
                  <br>
                  <input class="form-control input-sm" type="text" placeholder="Something Else">
                </div><!-- /.box-body -->
              </div>
				<label class="text"><span>Test Name</span>
					<div class="input-wrapper">
						<input type="text" name="testName" />
					</div></label> <label class="text"><span>My Name</span>
					<div class="input-wrapper">
						<input type="text" name="testName" />
					</div></label>
			</div>
			<div class="col-md-6"></div>
			<div class="row">
				<input type="submit" value="Submit" />
			</div>
		</div>
	</form>
</section>
