module Api
	module V1 
		class TablesController < ApplicationController
			respond_to :json
			protect_from_forgery
			skip_before_action :verify_authenticity_token, if: :json_request?
			
			def index
				respond_with Table.all
			end

			def create
				respond_with Table.create(dish_params_create)
			end

			def destroy
				respond_with Table.find(params[:id]).destroy
			end

			def update 
				respond_with Table.update(params[:id],dish_params_update)
			end

			protected
  			
  				def json_request?
    				request.format.json?
  				end

  				def dish_params_create
  					params.require(:table).permit(:id,:number)
  				end

  				def dish_params_update
  					params.require(:table).permit(:number)
  				end

		end
	end
end