module Api
	module V1 
		class DishesController < ApplicationController
			respond_to :json
			protect_from_forgery
			skip_before_action :verify_authenticity_token, if: :json_request?
			
			def index
				respond_with Dish.all
			end

			def show
				respond_with Dish.find(params[:id])
			end

			def create
				respond_with Dish.create(dish_params_create)
			end

			def destroy
				respond_with Dish.find(params[:id]).destroy
			end

			def update 
				respond_with Dish.update(params[:id],dish_params_update)
			end

			protected
  			
  				def json_request?
    				request.format.json?
  				end

  				def dish_params_create
  					params.require(:dish).permit(:id,:name,:price)
  				end

  				def dish_params_update
  					params.require(:dish).permit(:name,:price)
  				end

		end
	end
end