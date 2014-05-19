class TablesController < ApplicationController
  def new
  	@table = Table.new
  end

  def create
  	@table = Table.new(table_params)
  	if @table.save
  		redirect_to root_path
  	else
  		render 'new'
  	end
  end

  def index
  	@tables = Table.all
    #respond_to do |format|
    #  format.html
    #  format.json {render json:@tables}
    #end
  end

  def destroy
  	Table.find(params[:id]).destroy
  	redirect_to root_path
  end

  private
  
  def table_params
  	params.require(:table).permit(:number)
  end

end
