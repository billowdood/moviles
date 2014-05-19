class DishesController < ApplicationController
  def new
    @dish = Dish.new
  end

  def create
    @dish = Dish.new(dish_params)
    if @dish.save
      redirect_to root_path
    else
      render 'new'
    end
  end

  def index
    @dishes = Dish.all
  end

  def edit
    @dish = Dish.find(params[:id])
  end

  def update
    @dish = Dish.find(params[:id])
    if @dish.update_attributes(dish_params)
      redirect_to root_path
    else
      @dishes = Dish.all
      render 'index'
    end

  end

  def destroy
    Dish.find(params[:id]).destroy
    redirect_to root_path
  end

  private
    def dish_params
      params.require(:dish).permit(:name,:price)
    end
end
