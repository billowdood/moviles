class DishOrder < ActiveRecord::Base
	belongs_to :order
	has_one :dish	
end
