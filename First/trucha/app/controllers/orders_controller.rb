class OrdersController < ApplicationController
  def new
    @order = Order.new
  end

  def create
    @order = Order.new(order_params)
    if @order.save
      redirect_to root_path
    else
      render 'new'
    end
  end

  def index
    @orders = Order.all
    #respond_to do |format|
    #  format.html
    #  format.json {render json:@orders}
    end
  end

  def edit
    @order = Order.find(params[:id])
  end

  def update
    @order = Order.find(params[:id])
    if @order.update_attributes(order_params)
      redirect_to root_path
    else
      render 'index'
    end
  end

  def destroy
    Order.find(params[:id]).destroy
    @orders = Order.all
    render 'index'
  end

  private
  def order_params
    params.require(:order).permit(:table_id,:dish_id,:total,:is_payed)
  end
end
