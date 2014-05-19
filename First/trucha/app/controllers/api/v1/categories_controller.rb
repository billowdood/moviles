module Api
	module V1 
		class CategoriesController < ApplicationController
			respond_to :json
			protect_from_forgery
			skip_before_action :verify_authenticity_token, if: :json_request?
			
			def index
				respond_with Category.all
			end

			def show
				respond_with Category.find(params[:id]).dishes
				#respond_with Category.find_by_name(params[:id]).dishes
			end
			
			def create
				respond_with Category.create(dish_params_create)
			end

			def destroy
				respond_with Category.find(params[:id]).destroy
			end

			def update 
				respond_with Category.update(params[:id],dish_params_update)
			end

			protected
  			
  				def json_request?
    				request.format.json?
  				end

  				def dish_params_create
  					params.require(:category).permit(:id,:name)
  				end

  				def dish_params_update
  					params.require(:category).permit(:name)
  				end

		end
	end
end