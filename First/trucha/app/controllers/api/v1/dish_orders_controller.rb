module Api
	module V1 
		class DishOrdersController < ApplicationController
			respond_to :json
			protect_from_forgery
			skip_before_action :verify_authenticity_token, if: :json_request?
			
			def index
				respond_with DishOrder.all
			end

			def create
				#arrayDishOrder.each do |dishOrder|
				#	DishOrder.create(dish_order_params_create(dishOrder))
				#end
				#respond_with DishOrder.all
        respond_with DishOrder.all
			end

      def lastRow
        respond_with DishOrder.last
      end

			protected
  			
  				def json_request?
    				request.format.json?
  				end

  				def dish_order_params_create(dishOrder)
  					orderhash = eval(dishOrder.gsub(/:/,"=>"))
  					params = ActionController::Parameters.new(orderhash).require("dish_order").permit("id","dish_id","order_id")
  					return params
  				end

  				def arrayDishOrder
  					array = params.key(nil).split(/}{/)
  					logger.info "Working with array:#{array}"
  					(0..array.length-1).each do |el|
  						array[el].concat "}" if el == 0 or el != array.length-1
  						array[el] = "{"+array[el] if el != 0
  						logger.info "Element#{el}:#{array[el]}"
  					end
  					return array
  				end

		end
	end
end