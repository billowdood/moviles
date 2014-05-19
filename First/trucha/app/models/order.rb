class Order < ActiveRecord::Base
	has_many :dish_orders
	belongs_to :table
end
