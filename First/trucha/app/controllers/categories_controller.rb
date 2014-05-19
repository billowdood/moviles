class CategoriesController < ApplicationController
  def index
    @categories = Category.all
    #respond_to do |format|
    #  format.html
    #  format.json {render json: @categories}
    #end
  end

  def new
    @category = Category.new
  end

  def create
    @category = Category.new(category_params)
    if @category.save
      redirect_to root_path
    else
      @category = Category.new
      render 'new'
    end
  end

  def update
    @category = Category.find(params[:id])
    if @category.update_attributes(category_params)
      redirect_to root_path
    else
      @category = Category.find(params[:id])
      render 'index'
    end
  end

  def destroy
    @category = Category.find(params[:id])
    if @category.destroy
      redirect_to root_path
    else
      @category = Category.new
      redirect_to 'index'
    end
  end

  def edit
    @category = Category.find(params[:id])
  end

  private
  def category_params
    params.require(:category).permit(:name)
  end

end
