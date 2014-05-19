module Api
	module V1 
		class OrdersController < ApplicationController
			respond_to :json
			protect_from_forgery
			skip_before_action :verify_authenticity_token, if: :json_request?
			
			def index
				respond_with Order.all
			end

			def create
				logger.info "Working on create with params:#{params}"
				logger.info "After processing params:#{order_params_create}"
				respond_with Order.create(order_params_create)
			end

			def destroy
				respond_with Order.find(params[:id]).destroy
			end

			def update 
				respond_with Order.update(params[:id],order_params_update)
			end

			def lastRow
				respond_with Order.last
			end

			protected
  			
  				def json_request?
    				request.format.json?
  				end

  				def order_params_create
  					orderhash = eval(params.key(nil).gsub(/:/,"=>"))
  					params = ActionController::Parameters.new(orderhash).require("order").permit("id","table_id","total","is_payed")
  					return params
  				end

  				def order_params_update
  					params.require(:order).permit(:dish_id,:table_id,:total,:is_payed)
  				end

		end
	end
end